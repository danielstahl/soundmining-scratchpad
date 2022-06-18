package net.soundmining

import net.soundmining.Generative.{Picker, WeightedRandom, randomRange}
import net.soundmining.modular.ModularSynth.{lineControl, percControl, relativePercControl, relativeThreeBlockcontrol, staticControl}
import net.soundmining.modular.{SoundPlay, SynthPlayer}
import net.soundmining.synth.SuperColliderClient.loadDir
import net.soundmining.synth.{EmptyPatch, Instrument, Patch, PatchPlayback, SuperColliderClient, SuperColliderReceiver}

import scala.util.Random

object ConcreteMusic9 {
  implicit val client: SuperColliderClient = SuperColliderClient()
  implicit val random: Random = new Random()
  val SYNTH_DIR = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths"
  val SOUNDS_DIR = "/Users/danielstahl/Documents/Music/Pieces/Concrete Music/Concrete Music 9/sounds"
  val PAN1_1 = "pan1_1"
  val PAN1_2 = "pan1_2"
  val PAN1_3 = "pan1_3"
  val PAN1_4 = "pan1_4"
  val PAN2_1 = "pan2_1"
  val PAN2_2 = "pan2_2"
  val CHEST_1 = "chest_1"
  val DOOR_1 = "door_1"
  val HIT_1 = "hit_1"
  val HIT_2 = "hit_2"

  val soundPlays = Map(
    // 439, 946 (peak), 1915, 3009, 3682, 4596
    PAN1_1 -> SoundPlay(s"$SOUNDS_DIR/Pan 1 1.flac", 0.097, 0.510),
    // 998 (peak), 1923, 3681, 3050, 4588
    PAN1_2 -> SoundPlay(s"$SOUNDS_DIR/Pan 1 2.flac", 0.124, 0.522),
    // 123, 448, 949 (peak), 1924, 3084
    PAN1_3 -> SoundPlay(s"$SOUNDS_DIR/Pan 1 3.flac", 0.115, 0.523),
    // 460, 958 (peak), 1909, 3689
    PAN1_4 -> SoundPlay(s"$SOUNDS_DIR/Pan 1 4.flac", 0.128, 0.625),
    // 514, 1209 (peak), 1909, 3216, 4861
    PAN2_1 -> SoundPlay(s"$SOUNDS_DIR/Pan 2 1.flac", 0.102, 0.280),
    // 125, 430, 1180 (peak), 1920, 2829
    PAN2_2 -> SoundPlay(s"$SOUNDS_DIR/Pan 2 2.flac", 0.140, 0.321),
    // 156 (peak), 478, 1171, 2456
    CHEST_1 -> SoundPlay(s"$SOUNDS_DIR/Chest 1.flac", 0.505, 0.808),
    // 182 (peak), 907, 1293, 1737, 3058
    DOOR_1 -> SoundPlay(s"$SOUNDS_DIR/Door 1.flac", 0.303, 0.615),
    // 459, 762 (peak), 1193, 1848, 2762, 6278
    HIT_1 -> SoundPlay(s"$SOUNDS_DIR/Hit 1.flac", 0.574, 0.760),
    // 540 (peak), 1193, 3578, 7014
    HIT_2 -> SoundPlay(s"$SOUNDS_DIR/Hit 2.flac", 0.468, 0.690)
  )

  val synthPlayer = SynthPlayer(soundPlays = soundPlays, numberOfOutputBuses = 2)
  val patch = ResonatorCanon
  var patchPlayback: PatchPlayback = PatchPlayback(patch = patch, client = client)
  val superColliderReceiver: SuperColliderReceiver = SuperColliderReceiver(patchPlayback)

  object ChestResonator_v1 extends Patch {
    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val amp = velocity / 12.0
      val octave = (key / 12) - 1
      val note = key % 12

      // 156 (peak), 478, 1171, 2456
      val rate = 156.0 / 478.0
      val spectrum = Spectrum.makeSpectrum2(156, rate, 50)

      octave match {
        case 2 =>
          println(s"Octave 2 $note")
          synthPlayer()
            .pinkNoise(relativePercControl(0, amp * 0.007 * 0.5, 0.5, Left(Seq(-4, 4))))
            .bankOfResonators(Seq(spectrum(note), spectrum(note + 1), spectrum(note + 2)), Seq(1, 0.6, 0.4), Seq(3, 5, 8))
            .pan(staticControl(0))
            .playWithDuration(start, 13)
        case 3 =>
          println(s"Octave 3 $note")
          synthPlayer(CHEST_1)
            .playMono(0.1 + (0.2 / note), amp)
            .pan(staticControl(0))
            .play(start)
        case 4 =>
          println(s"Octave 4 $note")
          synthPlayer(CHEST_1)
            .playMono(1.0, amp)
            .ring(staticControl(spectrum(note)))
            .pan(staticControl(0))
            .play(start)
        case _ =>
      }
    }
  }

  object ResonatorCanon extends Patch {
    val seriesPicker = Picker((0 until 12))
    val chest1rate = 156.0 / 478.0
    val chest1spectrum = Spectrum.makeSpectrum2(156, chest1rate, 50)
    val door1rate = 182.0 / 907.0
    val door1spectrum = Spectrum.makeSpectrum2(182, door1rate, 50)
    val hit1rate = 459.0 / 762.0
    val hit1spectrum = Spectrum.makeSpectrum2(459, door1rate, 50)

    def firstResonator(series: Seq[Int], startTime: Double): Double = {
      var firstTime = startTime
      series.foreach(note => {
        val vel = randomRange(20, 100)
        val amp = vel / 127.0
        val duration = randomRange(8, 21)

        val panStart = randomRange(-0.8, 0.8)
        val panEnd = randomRange(-0.8, 0.8)

        synthPlayer()
          .pulse(staticControl(chest1spectrum(note)), relativeThreeBlockcontrol(0.001, 0.3, amp, amp, 0.4, 0.001, Left(Seq(0, 0, 0))))
          .ring(staticControl(chest1spectrum(note + 2)))
          .pan(lineControl(panStart - 0.1, panEnd + 0.1))
          .playWithDuration(firstTime + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))

        synthPlayer()
          .triangle(staticControl(chest1spectrum(note + 5)), relativeThreeBlockcontrol(0.001, 0.3, amp, amp, 0.4, 0.001, Left(Seq(0, 0, 0))))
          .ring(staticControl(chest1spectrum(note + 10)))
          .pan(lineControl(panStart + 0.1, panEnd - 0.1))
          .playWithDuration(firstTime + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))

        val firstDeltaTime = randomRange(8, 13)
        firstTime += firstDeltaTime
      })
      firstTime
    }

    def secondResonator(series: Seq[Int], startTime: Double): Double = {
      var secondTime = startTime

      series.foreach(note => {
        val vel = randomRange(20, 100)
        val amp = vel / 127.0
        val duration = randomRange(8, 21)

        val panStart = randomRange(-0.8, 0.8)
        val panEnd = randomRange(-0.8, 0.8)

        synthPlayer()
          .saw(staticControl(door1spectrum(note)), relativeThreeBlockcontrol(0.001, 0.1, amp, amp, 0.2, 0.001, Left(Seq(0, 0, 0))))
          .ring(staticControl(door1spectrum(note + 2)))
          .pan(lineControl(panStart - 0.1, panEnd + 0.1))
          .playWithDuration(secondTime + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))
        synthPlayer()
          .sine(staticControl(door1spectrum(note + 3)), relativeThreeBlockcontrol(0.001, 0.1, amp, amp, 0.2, 0.001, Left(Seq(0, 0, 0))))
          .ring(staticControl(door1spectrum(note + 6)))
          .pan(lineControl(panStart + 0.1, panEnd - 0.1))
          .playWithDuration(secondTime + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))

        val secondDeltaTime = randomRange(8, 13)
        secondTime += secondDeltaTime
      })
      secondTime
    }

    def thirdResonator(series: Seq[Int], startTime: Double): Double = {
      var thirdTime = startTime

      series.foreach(note => {
        val vel = randomRange(20, 100)
        val amp = vel / 127.0
        val duration = randomRange(8, 21)

        val panStart = randomRange(-0.8, 0.8)
        val panEnd = randomRange(-0.8, 0.8)

        synthPlayer()
          .sine(staticControl(hit1spectrum(note)), relativePercControl(0.001, amp, 0.5, Left(Seq(0, 0))))
          .ring(staticControl(hit1spectrum(note + 4)))
          .pan(lineControl(panStart - 0.1, panEnd + 0.1))
          .playWithDuration(thirdTime + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))
        synthPlayer()
          .triangle(staticControl(hit1spectrum(note + 7)), relativePercControl(0.001, amp, 0.5, Left(Seq(0, 0))))
          .ring(staticControl(hit1spectrum(note + 13)))
          .pan(lineControl(panStart + 0.1, panEnd - 0.1))
          .playWithDuration(thirdTime + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))

        val thirdDeltaTime = randomRange(8, 13)
        thirdTime += thirdDeltaTime
      })
      thirdTime
    }

    def dustPart(dustStart: Double, dustTotalLength: Double, dustDeltaMin: Double, dustDeltaMax: Double, velocity: Int): Double = {
      var dustTime = dustStart
      println(s"Dust total length $dustTotalLength")
      while(dustTime < (dustStart + dustTotalLength)) {
        DustPatch.staticHandle(dustTime, velocity)
        val dustDeltaTime = randomRange(dustDeltaMin, dustDeltaMax)
        dustTime += dustDeltaTime
      }
      dustTime
    }

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      staticHandle(start, velocity)
    }

    def playPiece(start: Double = 0, reset: Boolean = true): Unit = {
      if(reset) client.resetClock()
      val velocity = randomRange(45, 80).round.toInt
      staticHandle(start, velocity)
    }

    def staticHandle(start: Double, velocity: Int): Unit = {
      println(s"Piece with start $start and velocity $velocity")
      val series = seriesPicker.pick(11)
      val firstStartTime = start
      val firstLastTime = firstResonator(series, firstStartTime)
      println(s"First time start $firstStartTime end $firstLastTime")
      val secondStartTime = firstStartTime + randomRange(8, 13)
      val secondLastTime = secondResonator(series, secondStartTime)
      println(s"Second time start $secondStartTime end $secondLastTime")
      val thirdStartTime = secondStartTime + randomRange(8, 13)
      val thirdLastTime = thirdResonator(series, thirdStartTime)
      println(s"Third time start $thirdStartTime end $thirdLastTime")
      val dustStart1 = thirdStartTime + randomRange(8, 13)
      val dustTotalLength1 = randomRange(30.0, 60.0)
      val dust1LastTime = dustPart(dustStart1, dustTotalLength1, 5, 13, velocity)
      println(s"Dust1 time start $dustStart1 end $dust1LastTime")

      val dust2Start = firstLastTime + randomRange(8, 13)
      val dust2TotalLength = randomRange(30.0, 45.0)
      val dust2LastTime = dustPart(dust2Start, dust2TotalLength, 5, 8, velocity)
      println(s"Dust2 time start $dust2Start end $dust2LastTime")

      val series2 = series.reverse
      val thirdStartTime2 = dust2LastTime - randomRange(8, 13)
      val thirdLastTime2 = thirdResonator(series2, thirdStartTime2)
      println(s"Third2 time start $thirdStartTime2 end $thirdLastTime2")

      val secondStartTime2 = thirdStartTime2 + randomRange(8, 13)
      val secondLastTime2 = secondResonator(series2, secondStartTime2)
      println(s"Second2 time start $secondStartTime2 end $secondLastTime2")

      val firstStartTime2 = secondStartTime2 + randomRange(8, 13)
      val firstLastTime2 = firstResonator(series2, firstStartTime2)
      println(s"First2 time start $firstStartTime2 end $firstLastTime2")

      val dustStart12 = firstStartTime2 + randomRange(8, 13)
      val dustTotalLength12 = randomRange(30.0, 60.0)
      val dust1LastTime2 = dustPart(dustStart12, dustTotalLength12, 5, 13, velocity)
      println(s"Dust12 time start $dustStart12 end $dust1LastTime2")
    }
  }

  object ResonatorPatch extends Patch {
    // HIT_2 540 (peak), 1193, 3578, 7014

    // CHEST_1 156 (peak), 478, 1171, 2456
    val chest1rate = 156.0 / 478.0
    val chest1spectrum = Spectrum.makeSpectrum2(156, chest1rate, 50)

    // DOOR_1 182 (peak), 907, 1293, 1737, 3058
    val door1rate = 182.0 / 907.0
    val door1spectrum = Spectrum.makeSpectrum2(182, door1rate, 50)

    // HIT_1 459, 762 (peak), 1193, 1848, 2762, 6278
    val hit1rate = 459.0 / 762.0
    val hit1spectrum = Spectrum.makeSpectrum2(459, door1rate, 50)

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val amp = velocity / 127.0
      val octave = (key / 12) - 1
      val note = key % 12

      val duration = randomRange(5, 13)

      val panStart = randomRange(-0.8, 0.8)
      val panEnd = randomRange(-0.8, 0.8)

      println(s"play octave $octave note $note amp $amp pan start $panStart end $panEnd")
      octave match {
        case 2 =>
          synthPlayer()
            .pulse(staticControl(chest1spectrum(note)), relativeThreeBlockcontrol(0.001, 0.3, amp, amp, 0.4, 0.001, Left(Seq(0, 0, 0))))
            .ring(staticControl(chest1spectrum(note + 2)))
            .pan(lineControl(panStart - 0.1, panEnd + 0.1))
            .playWithDuration(start + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))

          synthPlayer()
            .triangle(staticControl(chest1spectrum(note + 5)), relativeThreeBlockcontrol(0.001, 0.3, amp, amp, 0.4, 0.001, Left(Seq(0, 0, 0))))
            .ring(staticControl(chest1spectrum(note + 10)))
            .pan(lineControl(panStart + 0.1, panEnd - 0.1))
            .playWithDuration(start + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))
        case 3 =>
          synthPlayer()
            .saw(staticControl(door1spectrum(note)), relativeThreeBlockcontrol(0.001, 0.1, amp, amp, 0.2, 0.001, Left(Seq(0, 0, 0))))
            .ring(staticControl(door1spectrum(note + 2)))
            .pan(lineControl(panStart - 0.1, panEnd + 0.1))
            .playWithDuration(start + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))
          synthPlayer()
            .sine(staticControl(door1spectrum(note + 3)), relativeThreeBlockcontrol(0.001, 0.1, amp, amp, 0.2, 0.001, Left(Seq(0, 0, 0))))
            .ring(staticControl(door1spectrum(note + 6)))
            .pan(lineControl(panStart + 0.1, panEnd - 0.1))
            .playWithDuration(start + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))

        case 4 =>
          synthPlayer()
            .sine(staticControl(hit1spectrum(note)), relativePercControl(0.001, amp, 0.5, Left(Seq(0, 0))))
            .ring(staticControl(hit1spectrum(note + 4)))
            .pan(lineControl(panStart - 0.1, panEnd + 0.1))
            .playWithDuration(start + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))
          synthPlayer()
            .triangle(staticControl(hit1spectrum(note + 7)), relativePercControl(0.001, amp, 0.5, Left(Seq(0, 0))))
            .ring(staticControl(hit1spectrum(note + 13)))
            .pan(lineControl(panStart + 0.1, panEnd - 0.1))
            .playWithDuration(start + randomRange(0, 0.1), duration * randomRange(0.9, 1.0))
        case 5 =>
          DustPatch.noteHandle(start, key, velocity, device)
        case _ =>
      }


    }
  }

  object DustPatch extends Patch {
    val sounds = WeightedRandom(Seq(
      (CHEST_1, 0.2), (DOOR_1, 0.2), (HIT_1, 0.3), (HIT_2, 0.3)))

    val timeGenerator = WeightedRandom(Seq(
      (() => randomRange(0.03, 0.2), 0.6),
      (() => randomRange(0.3, 0.9), 0.4)))

    val totalLengthGenerator = WeightedRandom(Seq(
      (() => randomRange(1, 3), 0.6),
      (() => randomRange(4, 8), 0.4)))

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      staticHandle(start, velocity)
    }

    def staticHandle(start: Double, velocity: Int): Unit = {
      val amp = velocity / 12.0

      val totalLength = totalLengthGenerator.choose().apply()

      val pan = randomRange(-0.8, 0.8)
      var time = start
      println(s"Dust start $start total length $totalLength amp $amp center pan $pan")

      while (time < start + totalLength) {
        val sound = sounds.choose()
        val localPan = randomRange(pan - 0.2, pan + 0.2)
        val deltaTime = timeGenerator.choose().apply()
        val localAmp = amp * deltaTime

        val picker = sound match {
          case CHEST_1 => CHEST1_PICKER
          case DOOR_1 => DOOR1_PICKER
          case HIT_1 => HIT1_PICKER
          case HIT_2 => HIT2_PICKER
        }
        val picked = random.nextInt(3) + 2
        picker.pick(picked)
          .foreach(playFunc => playFunc(time, localAmp, localPan))

        time = time + deltaTime
      }
    }
  }

  val CHEST1_RATE = 156.0 / 478.0

  val CHEST1_VARIANTS = Seq(
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(CHEST_1)
        .playMono(1, amp)
        .pan(staticControl(pan))
        .play(start)
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(CHEST_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 10)
        .lowPass(staticControl(156 * CHEST1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start + randomRange(0.0, 0.0001))
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(CHEST_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp)
        .lowPass(staticControl(156))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start + randomRange(0.0, 0.0001))
    },

    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(CHEST_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 30)
        .lowPass(staticControl(478))
        .highPass(staticControl(1171))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start + randomRange(0.0, 0.0001))
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(CHEST_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 10)
        .highPass(staticControl(1171))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start - randomRange(0.0, 0.0001))
    })
  val CHEST1_PICKER = Picker(CHEST1_VARIANTS)

  // 182 (peak), 907, 1293, 1737, 3058
  val DOOR1_RATE = 182.0 / 907.0
  val DOOR1_VARIANTS = Seq(
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(DOOR_1)
        .playMono(1.0, amp)
        .pan(staticControl(pan))
        .play(start)
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(DOOR_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 10)
        .lowPass(staticControl(182 * DOOR1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(DOOR_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp)
        .lowPass(staticControl(182))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(DOOR_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 30)
        .lowPass(staticControl(907))
        .highPass(staticControl(1737))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start + randomRange(0.0, 0.0001))
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(DOOR_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 10)
        .highPass(staticControl(1737))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start - randomRange(0.0, 0.0001))
    })

  val DOOR1_PICKER = Picker(DOOR1_VARIANTS)

  val HIT1_RATE = 459.0 / 762.0

  val HIT1_VARIANTS = Seq(
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_1)
        .playMono(1.0, amp)
        .pan(staticControl(pan))
        .play(start)
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 10)
        .lowPass(staticControl(459 * HIT1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp)
        .lowPass(staticControl(459))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 30)
        .lowPass(staticControl(762))
        .highPass(staticControl(1848))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start + randomRange(0.0, 0.0001))
    },
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_1)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 10)
        .highPass(staticControl(1848))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start - randomRange(0.0, 0.0001))
    })

  val HIT1_PICKER = Picker(HIT1_VARIANTS)

  val HIT2_RATE = 540.0 / 1193.0

  val HIT2_VARIANTS = Seq(
    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_2)
        .playMono(1.0, amp)
        .pan(staticControl(pan))
        .play(start)
    },

    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_2)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 10)
        .lowPass(staticControl(540 * HIT2_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)
    },

    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_2)
        .playMono(1 + randomRange(-0.001, 0.001), amp)
        .lowPass(staticControl(540))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)
    },

    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_2)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 30)
        .lowPass(staticControl(1193))
        .highPass(staticControl(3578))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start + randomRange(0.0, 0.0001))
    },

    (start: Double, amp: Double, pan: Double) => {
      synthPlayer(HIT_2)
        .playMono(1 + randomRange(-0.001, 0.001), amp * 10)
        .highPass(staticControl(3578))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start - randomRange(0.0, 0.0001))
    }
  )

  val HIT2_PICKER = Picker(HIT2_VARIANTS)

  object PlayHit extends Patch {
    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val amp = velocity / 12.0

      val note = key % 12

      note match {
        case 0 =>
          // 156 (peak), 478, 1171, 2456
          val picked = random.nextInt(3) + 2
          CHEST1_PICKER.pick(picked)
            .foreach(playFunc => playFunc(start, amp, 0))
        case 2 =>
          // 182 (peak), 907, 1293, 1737, 3058
          val picked = random.nextInt(3) + 2
          DOOR1_PICKER.pick(picked)
            .foreach(playFunc => playFunc(start, amp, 0))
        case 4 =>
          // 459, 762 (peak), 1193, 1848, 2762, 6278
          val picked = random.nextInt(3) + 2
          HIT1_PICKER.pick(picked)
            .foreach(playFunc => playFunc(start, amp, 0))
        case 5 =>
          // 540 (peak), 1193, 3578, 7014
          val picked = random.nextInt(3) + 2
          println(s"Hit 2 $picked")
          HIT2_PICKER.pick(picked)
            .foreach(playFunc => playFunc(start, amp, 0))
        case _ =>
      }
    }
  }

  object PlayHitSimple extends Patch {
    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val amp = velocity / 12.0

      val note = key % 12

      val sound = note match {
        case 0 => CHEST_1
        case 2 => DOOR_1
        case 4 => HIT_1
        case 5 => HIT_2
        case _ => CHEST_1
      }
      println(s"Play note $note sound $sound")

      synthPlayer(sound)
        .playMono(1.0, amp)
        .pan(staticControl(0))
        .play(start)
    }
  }

  def init(): Unit = {
    println("Starting up SuperCollider client")
    client.start
    Instrument.setupNodes(client)
    client.send(loadDir(SYNTH_DIR))
    synthPlayer.init()
    superColliderReceiver.start()
  }

  def stop(): Unit = {
    println("Stopping SuperCollider client")
    synthPlayer.stop()
    client.stop
    superColliderReceiver.stop()
  }
}
