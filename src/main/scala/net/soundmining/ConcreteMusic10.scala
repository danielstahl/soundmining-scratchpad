package net.soundmining

import net.soundmining.Generative.{WeightedRandom, randomRange}
import net.soundmining.modular.ModularSynth.{lineControl, percControl, relativePercControl, staticControl}
import net.soundmining.synth.SuperColliderClient.loadDir
import net.soundmining.synth.{EmptyPatch, Instrument, Patch, PatchPlayback, SuperColliderClient, SuperColliderReceiver}
import ConcreteMusic10Common._
import StoneHitSound._
import StoneScratchSound._
import WoodHitSound._
import WoodScratchSound._
import MetalHitSound._
import MetalScratchSound._
import scala.annotation.tailrec

object ConcreteMusic10 {
  val SYNTH_DIR = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths"

  val patch = Part3Patch
  var patchPlayback: PatchPlayback = PatchPlayback(patch = patch, client = client)
  val superColliderReceiver: SuperColliderReceiver = SuperColliderReceiver(patchPlayback)

  object WoodHarmonicPatch2 extends Patch {
    var variantNote = 5

    def getVariants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = {
      variantNote match {
        case 0 => WoodHit1HarmonicVariants.variants
        case 1 => WoodHit2HarmonicVariants.variants
        case 2 => WoodScratch1HarmonicVariants.variants
        case 3 => WoodScratch2HarmonicVariants.variants
        case 4 => WoodScratch3HarmonicVariants.variants
        case 5 => WoodScratch4HarmonicVariants.variants
      }
    }

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val note = key % 12
      val octave = (key / 12) - 1
      val amp = velocity / 127.0

      println(s"Note $note octave $octave velocity $velocity $device")

      device match {
        case "PAD:nanoPAD2" =>
          variantNote = note
          println(s"Variant $note")
        case _ =>
          val variants = getVariants
          val variant = octave match {
            case 2 =>
              variants(LOW_SOUND).head
            case 3 =>
              variants(LOW_SOUND)(1)
            case 4 =>
              variants(MIDDLE_SOUND).head
            case 5 =>
              variants(HIGH_SOUND).head
          }
          variant(start, note, amp, 0.5, randomRange(5, 8), randomRange(-0.8, 0.8))
      }

    }
  }

  object MetalHarmonicPatch extends Patch {
    var variantNote = 1

    def getVariants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = {
      variantNote match {
        case 0 => MetalHit1HarmonicVariants.variants
        case 1 => MetalHit2HarmonicVariants.variants
      }
    }

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val note = key % 12
      val octave = (key / 12) - 1
      val amp = velocity / 127.0

      println(s"Note $note octave $octave velocity $velocity $device")

      device match {
        case "PAD:nanoPAD2" =>
          variantNote = note
          println(s"Variant $note")
        case _ =>
          val variants = getVariants
          val variant = octave match {
            case 2 =>
              variants(LOW_SOUND).head
            case 3 =>
              variants(LOW_SOUND)(1)
            case 4 =>
              variants(MIDDLE_SOUND).head
            case 5 =>
              variants(HIGH_SOUND).head
          }
          variant(start, note, amp, 0.5, randomRange(5, 8), randomRange(-0.8, 0.8))
      }

    }
  }

  object StoneHarmonicPatch extends Patch {
    var variantNote = 0

    def getVariants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = {
      variantNote match {
        case 0 => StoneHit1HarmonicVariants.variants
      }
    }

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val note = key % 12
      val octave = (key / 12) - 1
      val amp = velocity / 127.0

      println(s"Note $note octave $octave velocity $velocity $device")

      device match {
        case "PAD:nanoPAD2" =>
          variantNote = note
          println(s"Variant $note")
        case _ =>
          val variants = getVariants
          val variant = octave match {
            case 2 =>
              variants(LOW_SOUND).head
            case 3 =>
              variants(LOW_SOUND)(1)
            case 4 =>
              variants(MIDDLE_SOUND).head
            case 5 =>
              variants(HIGH_SOUND).head
          }
          variant(start, note, amp, 0.5, randomRange(5, 8), randomRange(-0.8, 0.8))
      }

    }
  }

  object WoodHitHarmonicPatch extends Patch {
    // Melody. 0 1 0 1 (at 0.66)

    // 613 (peak), 1044, 1955, 3052, 4684, 6199
    val WOOD_HIT_1_RATIO = 1044.0 / 613.0
    val WOOD_HIT_1_INV_RATIO = 613.0 / 1044.0

    val WOOD_HIT_1_RATIO_SPECTRUM = Spectrum.makeSpectrum2(613 * WOOD_HIT_1_INV_RATIO * WOOD_HIT_1_INV_RATIO, WOOD_HIT_1_RATIO, 50)
    val WOOD_HIT_1_INV_RATIO_SPECTRUM = Spectrum.makeSpectrum2(613 * WOOD_HIT_1_INV_RATIO * WOOD_HIT_1_INV_RATIO, WOOD_HIT_1_INV_RATIO, 50)

    // 436 (peak), 977, 1443, 2308, 4603, 5312
    val WOOD_HIT_2_RATIO = 977.0 / 436.0
    val WOOD_HIT_2_INV_RATIO = 436.0 / 977.0

    val WOOD_HIT_2_RATIO_SPECTRUM = Spectrum.makeSpectrum2(613 * WOOD_HIT_2_INV_RATIO * WOOD_HIT_2_INV_RATIO, WOOD_HIT_2_RATIO, 50)
    val WOOD_HIT_2_INV_RATIO_SPECTRUM = Spectrum.makeSpectrum2(613 * WOOD_HIT_2_INV_RATIO * WOOD_HIT_2_INV_RATIO, WOOD_HIT_2_INV_RATIO, 50)

    def playHarmony1(start: Double, spectrum: Seq[Double], note: Int, amp: Double): Unit = {
      synthPlayer()
        .pulse(staticControl(WOOD_HIT_1_RATIO_SPECTRUM(note)), relativePercControl(0.0001, amp, 0.33, Left(Seq(0, 0))))
        .ring(staticControl(WOOD_HIT_1_RATIO_SPECTRUM(note + 1)))
        .ring(staticControl(WOOD_HIT_1_RATIO_SPECTRUM(note + 2)))
        .pan(staticControl(0))
        .playWithDuration(start, randomRange(5, 8))

      synthPlayer()
        .saw(staticControl(spectrum(note + 5)), relativePercControl(0.0001, amp, 0.5, Left(Seq(0, 0))))
        .ring(staticControl(spectrum(note + 6)))
        .ring(staticControl(spectrum(note + 7)))
        .pan(staticControl(0.2))
        .playWithDuration(start, randomRange(5, 8))

      synthPlayer()
        .triangle(staticControl(spectrum(note + 10)), relativePercControl(0.0001, amp, 0.66, Left(Seq(0, 0))))
        .ring(staticControl(spectrum(note + 1)))
        .ring(staticControl(spectrum(note + 12)))
        .pan(staticControl(-0.2))
        .playWithDuration(start, randomRange(5, 8))
    }

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val note = key % 12
      val octave = (key / 12) - 1
      val amp = velocity / 127.0

      println(s"Note $note octave $octave velocity $velocity")

      octave match {
        case 2 =>
          playHarmony1(start, WOOD_HIT_1_RATIO_SPECTRUM, note, amp)
        case 3 =>
          playHarmony1(start, WOOD_HIT_1_INV_RATIO_SPECTRUM, note, amp)
        case 4 =>
          playHarmony1(start, WOOD_HIT_2_RATIO_SPECTRUM, note, amp)
        case 5 =>
          playHarmony1(start, WOOD_HIT_2_INV_RATIO_SPECTRUM, note, amp)
        case _ =>
      }
    }
  }

  object SoundPlayback extends Patch {
    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val amp = velocity / 127.0

      val sound = key match {
        case 36 => METAL_HIT_1
        case 37 => METAL_HIT_2
        case 38 => METAL_SCRATCH_1
        case 39 => METAL_SCRATCH_2
        case 40 => STONE_HIT_1
        case 41 => STONE_HIT_2
        case 42 => STONE_SCRATCH_1
        case 43 => STONE_SCRATCH_2
        case 44 => STONE_SCRATCH_3
        case 45 => STONE_SCRATCH_4
        case 46 => WOOD_HIT_1
        case 47 => WOOD_HIT_2
        case 48 => WOOD_SCRATCH_1
        case 49 => WOOD_SCRATCH_2
        case 50 => WOOD_SCRATCH_3
        case 51 => WOOD_SCRATCH_4
        case _ => METAL_HIT_1
      }

      synthPlayer(sound)
        .playMono(1.0, amp)
        .pan(staticControl(0))
        .play(start)
    }
  }

  trait DynamicWeightedRandom[T] {
    def choose(position: Double, totalLength: Double): T
  }

  case class LineWeightedDynamicRandom[T](pairs: Seq[(T, (Double, Double))]) extends DynamicWeightedRandom[T] {


    def getSortedPairs(position: Double, totalLength: Double): Seq[(T, Double)] = {
      val relativePosition = position / totalLength
      val relativePairs = pairs.map {
        case (value, (start, end)) => (value, start + ((end - start) * relativePosition))
      }
      val fact = 1.0 / relativePairs.map {
        case (_, probability) => probability
      }.sum

      relativePairs.map {
        case (value, probability) => (value, probability * fact)
      }.sortBy {
        case (_, probability) => probability
      }.reverse

    }

    @tailrec
    private def chooseValue(weightedRandom: Double, pairsToChoose: Seq[(T, Double)]): T = {
      pairsToChoose match {
        case (value, probability) :: _ if weightedRandom <= probability => value
        case (_, probability) :: xs => chooseValue(weightedRandom - probability, xs)
      }
    }

    override def choose(position: Double, totalLength: Double): T = {
      chooseValue(random.nextDouble(), getSortedPairs(position, totalLength))
    }
  }


  object DynamicCloudPatch extends Patch {
    val stoneHitSounds = makeEvenWeightedRandom(STONE_HITS)
    val woodHitSounds = makeEvenWeightedRandom(WOOD_HITS)
    val hitSounds = LineWeightedDynamicRandom(Seq((stoneHitSounds, (0.99, 0.01)), (woodHitSounds, (0.01, 0.99))))

    val hitTimeGenerator = WeightedRandom(Seq(
      (() => randomRange(0.03, 0.2), 0.6),
      (() => randomRange(0.3, 0.9), 0.4)))

    val scratchTimeGenerator = WeightedRandom(Seq(
      (() => randomRange(0.1, 0.5), 0.6),
      (() => randomRange(1.0, 2), 0.4)))

    val totalLengthGenerator = WeightedRandom(Seq(
      (() => randomRange(1, 8), 0.6),
      (() => randomRange(8, 13), 0.4)))

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {

      val amp = velocity / 127.0

      val totalLength = totalLengthGenerator.choose().apply()

      val pan = randomRange(-0.8, 0.8)
      var time = start
      while (time < start + totalLength) {
        val sound = hitSounds.choose(time - start, totalLength).choose()
        val localPan = randomRange(pan - 0.2, pan + 0.2)
        val deltaTime = hitTimeGenerator.choose().apply()
        val localAmp = amp * deltaTime

        synthPlayer(sound)
          .playMono(1.0, localAmp)
          .pan(staticControl(localPan))
          .play(time)

        time = time + deltaTime
      }

    }
  }

  object CloudPatch3 extends Patch {
    val totalLengthGenerator = WeightedRandom(Seq(
      (() => randomRange(1, 3), 0.6),
      (() => randomRange(4, 8), 0.4)))

    val hitTimeGenerator = WeightedRandom(Seq(
      (() => randomRange(0.03, 0.2), 0.6),
      (() => randomRange(0.3, 0.9), 0.4)))

    val scratchTimeGenerator = WeightedRandom(Seq(
      (() => randomRange(0.1, 0.5), 0.6),
      (() => randomRange(1.0, 2), 0.4)))

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {

      val amp = velocity / 127.0
      val totalLength = totalLengthGenerator.choose().apply()

      val pan = randomRange(-0.8, 0.8)
      var time = start
      println(s"Cloud start key $key $start total length $totalLength amp $amp center pan $pan")

      val (timeGenerator, instrument, variant) = key match {
        case 36 =>
          (hitTimeGenerator, STONE_HIT_MIXES, LOW_SOUND)
        case 37 =>
          (hitTimeGenerator, STONE_HIT_MIXES, LOW_MIDDLE_SOUND)
        case 38 =>
          (hitTimeGenerator, STONE_HIT_MIXES, MIDDLE_SOUND)
        case 39 =>
          (hitTimeGenerator, STONE_HIT_MIXES, MIDDLE_HIGH_SOUND)
        case 40 =>
          (hitTimeGenerator, STONE_HIT_MIXES, HIGH_SOUND)

        case 43 =>
          (scratchTimeGenerator, STONE_SCRATCH_MIXES, LOW_SOUND)
        case 44 =>
          (scratchTimeGenerator, STONE_SCRATCH_MIXES, LOW_MIDDLE_SOUND)
        case 45 =>
          (scratchTimeGenerator, STONE_SCRATCH_MIXES, MIDDLE_SOUND)
        case 46 =>
          (scratchTimeGenerator, STONE_SCRATCH_MIXES, MIDDLE_HIGH_SOUND)
        case 47 =>
          (scratchTimeGenerator, STONE_SCRATCH_MIXES, HIGH_SOUND)

        case 48 =>
          (hitTimeGenerator, WOOD_HIT_MIXES, LOW_SOUND)
        case 49 =>
          (hitTimeGenerator, WOOD_HIT_MIXES, LOW_MIDDLE_SOUND)
        case 50 =>
          (hitTimeGenerator, WOOD_HIT_MIXES, MIDDLE_SOUND)
        case 51 =>
          (hitTimeGenerator, WOOD_HIT_MIXES, MIDDLE_HIGH_SOUND)
        case 52 =>
          (hitTimeGenerator, WOOD_HIT_MIXES, HIGH_SOUND)

        case 55 =>
          (scratchTimeGenerator, WOOD_SCRATCH_MIXES, LOW_SOUND)
        case 56 =>
          (scratchTimeGenerator, WOOD_SCRATCH_MIXES, LOW_MIDDLE_SOUND)
        case 57 =>
          (scratchTimeGenerator, WOOD_SCRATCH_MIXES, MIDDLE_SOUND)
        case 58 =>
          (scratchTimeGenerator, WOOD_SCRATCH_MIXES, MIDDLE_HIGH_SOUND)
        case 59 =>
          (scratchTimeGenerator, WOOD_SCRATCH_MIXES, HIGH_SOUND)

        case 60 =>
          (hitTimeGenerator, METAL_HIT_MIXES, LOW_SOUND)
        case 61 =>
          (hitTimeGenerator, METAL_HIT_MIXES, LOW_MIDDLE_SOUND)
        case 62 =>
          (hitTimeGenerator, METAL_HIT_MIXES, MIDDLE_SOUND)
        case 63 =>
          (hitTimeGenerator, METAL_HIT_MIXES, MIDDLE_HIGH_SOUND)
        case 64 =>
          (hitTimeGenerator, METAL_HIT_MIXES, HIGH_SOUND)

        case 67 =>
          (scratchTimeGenerator, METAL_SCRATCH_MIXES, LOW_SOUND)
        case 68 =>
          (scratchTimeGenerator, METAL_SCRATCH_MIXES, LOW_MIDDLE_SOUND)
        case 69 =>
          (scratchTimeGenerator, METAL_SCRATCH_MIXES, MIDDLE_SOUND)
        case 70 =>
          (scratchTimeGenerator, METAL_SCRATCH_MIXES, MIDDLE_HIGH_SOUND)
        case 71 =>
          (scratchTimeGenerator, METAL_SCRATCH_MIXES, HIGH_SOUND)
        case _ =>
          (hitTimeGenerator, METAL_HIT_MIXES, LOW_SOUND)
      }
      while (time < start + totalLength) {
        val localPan = randomRange(pan - 0.2, pan + 0.2)
        val localTime = timeGenerator.choose().apply()
        val localAmp = amp * localTime
        instrument.playVariant(variant, time, localAmp, localPan)
        time = time + localTime
      }

    }
  }

  object CloudPatch extends Patch {
    val stoneHitSounds = makeEvenWeightedRandom(STONE_HITS)

    val stoneScratchSounds = makeEvenWeightedRandom(STONE_SCRATCHES)

    val woodHitSounds = makeEvenWeightedRandom(WOOD_HITS)

    val woodScratchSounds = makeEvenWeightedRandom(WOOD_SCRATCHES)

    val metalHitSounds = makeEvenWeightedRandom(METAL_HITS)

    val metalScratchSounds = makeEvenWeightedRandom(METAL_SCRATCHES)

    val hitSounds = WeightedRandom(Seq(
      (stoneHitSounds, 0.5),
      (woodHitSounds, 0.4),
      (metalHitSounds, 0.1)))

    val scratchSounds = WeightedRandom(Seq(
      (stoneScratchSounds, 0.5),
      (woodScratchSounds, 0.4),
      (metalScratchSounds, 0.1)))

    val hitTimeGenerator = WeightedRandom(Seq(
      (() => randomRange(0.03, 0.2), 0.6),
      (() => randomRange(0.3, 0.9), 0.4)))

    val scratchTimeGenerator = WeightedRandom(Seq(
      (() => randomRange(0.1, 0.5), 0.6),
      (() => randomRange(1.0, 2), 0.4)))

    val totalLengthGenerator = WeightedRandom(Seq(
      (() => randomRange(1, 3), 0.6),
      (() => randomRange(4, 8), 0.4)))

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {

      val amp = velocity / 127.0

      val totalLength = totalLengthGenerator.choose().apply()

      val pan = randomRange(-0.8, 0.8)
      var time = start
      println(s"Cloud start $start total length $totalLength amp $amp center pan $pan")

      val (soundChooser, timeGenerator) = key match {
        case 36 => (hitSounds, hitTimeGenerator)
        case 37 => (scratchSounds, scratchTimeGenerator)
        case _ => (hitSounds, hitTimeGenerator)
      }
      while (time < start + totalLength) {
        val sound = soundChooser.choose().choose()
        val localPan = randomRange(pan - 0.2, pan + 0.2)
        val deltaTime = timeGenerator.choose().apply()
        val localAmp = amp * deltaTime

        synthPlayer(sound)
          .playMono(1.0, localAmp)
          .pan(staticControl(localPan))
          .play(time)

        time = time + deltaTime
      }

    }
  }

  case class CloudInstrument(totalLengthGenerator: WeightedRandom[() => Double],
                             timeGenerator: WeightedRandom[() => Double],
                             soundGenerator: WeightedRandom[(() => MaterialMixes, SoundVariant)],
                             ampGenerator: (Double, Double) => Double) {
    def playCloud(start: Double, overallAmp: Double): Double = {
      val totalLength = totalLengthGenerator.choose().apply()
      val pan = randomRange(-0.8, 0.8)
      println(s"Cloud start $start total length $totalLength amp $overallAmp center pan $pan")
      var time = start
      while (time < start + totalLength) {
        val localPan = randomRange(pan - 0.2, pan + 0.2)
        val localTime = timeGenerator.choose().apply()
        val localAmp = ampGenerator(overallAmp, localTime)
        val (materialMix, variant) = soundGenerator.choose()
        materialMix.apply().playVariant(variant, time, localAmp, localPan)
        time = time + localTime
      }
      totalLength
    }
  }

  object Part3Patch extends Patch {

    val lowStoneInstrument = CloudInstrument(
      totalLengthGenerator = WeightedRandom(Seq(
        (() => randomRange(5, 8), 0.4),
        (() => randomRange(8, 13), 0.6))),
      timeGenerator = WeightedRandom(Seq(
        (() => randomRange(0.03, 0.25), 0.3),
        (() => randomRange(0.60, 1.30), 0.6))),
      soundGenerator = WeightedRandom(Seq(
        ((() => STONE_HIT_MIXES, LOW_SOUND), 0.50),
        ((() => STONE_HIT_MIXES, LOW_MIDDLE_SOUND), 0.30),
        ((() => STONE_SCRATCH_MIXES, LOW_SOUND), 0.10),
        ((() => STONE_SCRATCH_MIXES, LOW_MIDDLE_SOUND), 0.10)
      )),
      ampGenerator = (overallAmp, localTime) => overallAmp * (localTime * randomRange(0.3, 0.5) * 7))

    val middleStoneInstrument = CloudInstrument(
      totalLengthGenerator = WeightedRandom(Seq(
        (() => randomRange(3, 5), 0.2),
        (() => randomRange(2, 3), 0.8))),
      timeGenerator = WeightedRandom(Seq(
        (() => randomRange(0.03, 0.15), 0.80),
        (() => randomRange(0.35, 0.9), 0.20))),
      soundGenerator = WeightedRandom(Seq(
        ((() => STONE_HIT_MIXES, MIDDLE_HIGH_SOUND), 0.92),
        ((() => STONE_SCRATCH_MIXES, MIDDLE_HIGH_SOUND), 0.08))),
      ampGenerator = (overallAmp, localTime) => overallAmp * (localTime * randomRange(0.3, 0.5) * 7))

    // long, long, short, pause. long long long pause. Repeat
    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val amp = velocity / 127.0

      device match {
        case "PAD:nanoPAD2" =>
          key match {
            case 36 => lowStoneInstrument.playCloud(start, amp)
            case 37 => middleStoneInstrument.playCloud(start, amp)
            case _ =>
          }
        case "KEYBOARD:microKEY2" =>
          StoneHarmonicPatch.noteHandle(start, key, velocity, device)
      }
    }


    def play(start: Double, reset: Boolean = true): Double = {
      if (reset) client.resetClock()

      println(s"Part 2 start $start")

      var time = start
      0 until 8 foreach {
        _ =>
          var overallAmp = randomRange(0.2, 0.8)
          var cloudLen = lowStoneInstrument.playCloud(time, overallAmp)
          time = time + (cloudLen * randomRange(0.80, 0.99))

          overallAmp = randomRange(0.2, 0.8)
          cloudLen = lowStoneInstrument.playCloud(time, overallAmp)
          time = time + (cloudLen * randomRange(0.80, 0.99))

          overallAmp = randomRange(0.2, 0.8)
          cloudLen = middleStoneInstrument.playCloud(time, overallAmp)
          time = time + (cloudLen * randomRange(2.0, 2.25))
      }

      var nextStart = start + randomRange(13, 21)
      nextStart = playHarmonicVariant1(nextStart, randomRange(0.5, 0.75), 0)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.25, 1.50), 1)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.5, 0.75), 0)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.25, 1.50), 3)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.5, 0.75), 5)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.25, 1.50), 2)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.5, 0.75), 4)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.25, 1.50), 1)

      nextStart = nextStart + randomRange(13, 21)
      nextStart = playHarmonicVariant2(nextStart, randomRange(0.5, 0.75), 0)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.25, 1.50), 1)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.5, 0.75), 0)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.25, 1.50), 3)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.5, 0.75), 5)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.25, 1.50), 2)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.5, 0.75), 4)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.25, 1.50), 1)
      time
    }

    def playHarmonicVariant1(start: Double, nextStartFactor: Double, note: Int): Double = {
      println(s"Harmonic variant 1 $start note $note")
      val harmonicVariant = StoneHit1HarmonicVariants.variants(LOW_SOUND)(1)
      val lowHarmonicVariant = StoneHit1HarmonicVariants.variants(MIDDLE_SOUND).head
      val duration = randomRange(5, 8)
      val amp = randomRange(0.2, 0.8)
      val pan = randomRange(-0.8, 0.8)
      harmonicVariant(
        start, // start
        note,
        amp, // amp
        randomRange(0.33, 0.66), // peak
        duration, //duration
        pan) // pan
      lowHarmonicVariant(
        start + randomRange(-0.4, 1.0), // start
        note,
        amp * randomRange(0.5, 0.7), //amp
        randomRange(0.33, 0.66), // peak
        duration * randomRange(0.8, 1), // duration
        pan * randomRange(-0.9, -1.1)) // pan
      start + (duration * nextStartFactor)
    }

    def playHarmonicVariant2(start: Double, nextStartFactor: Double, note: Int): Double = {
      println(s"Harmonic variant 2 $start note $note")
      val harmonicVariant = StoneHit1HarmonicVariants.variants(LOW_SOUND).head
      val middleHarmonicVariant = StoneHit1HarmonicVariants.variants(HIGH_SOUND).head
      val duration = randomRange(5, 8)
      val amp = randomRange(0.2, 0.8)
      val pan = randomRange(-0.8, 0.8)

      harmonicVariant(
        start, // start
        note,
        amp, // amp
        randomRange(0.33, 0.66), // peak
        duration, //duration
        pan) // pan
      middleHarmonicVariant(
        start + randomRange(-0.4, 1.0), // start
        note,
        amp, //amp
        randomRange(0.33, 0.66), // peak
        duration * randomRange(0.8, 1), // duration
        pan * randomRange(-0.9, -1.1)) // pan
      start + (duration * nextStartFactor)
    }


  }

  object Part2Patch extends Patch {

    val middleMetalInstrument = CloudInstrument(
      totalLengthGenerator = WeightedRandom(Seq(
        (() => randomRange(3, 5), 0.8),
        (() => randomRange(5, 8), 0.2))),
      timeGenerator = WeightedRandom(Seq(
        (() => randomRange(0.03, 0.25), 0.80),
        (() => randomRange(0.75, 1.25), 0.20))),
      soundGenerator = WeightedRandom(Seq(
        ((() => METAL_HIT_MIXES, MIDDLE_SOUND), 0.92),
        ((() => METAL_SCRATCH_MIXES, MIDDLE_SOUND), 0.08))),
      ampGenerator = (overallAmp, localTime) => overallAmp * (localTime * randomRange(0.3, 0.5) * 7))

    val middleHighMetalInstrument = CloudInstrument(
      totalLengthGenerator = WeightedRandom(Seq(
        (() => randomRange(3, 5), 0.8),
        (() => randomRange(2, 3), 0.2))),
      timeGenerator = WeightedRandom(Seq(
        (() => randomRange(0.03, 0.15), 0.80),
        (() => randomRange(0.65, 1.1), 0.20))),
      soundGenerator = WeightedRandom(Seq(
        ((() => METAL_HIT_MIXES, MIDDLE_HIGH_SOUND), 0.9),
        ((() => METAL_SCRATCH_MIXES, HIGH_SOUND), 0.1))),
      ampGenerator = (overallAmp, localTime) => overallAmp * (localTime * randomRange(0.3, 0.5) * 7))

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val amp = velocity / 127.0

      device match {
        case "PAD:nanoPAD2" =>
          key match {
            case 36 => middleMetalInstrument.playCloud(start, amp)
            case 37 => middleHighMetalInstrument.playCloud(start, amp)
            case _ =>
          }
        case "KEYBOARD:microKEY2" =>
          MetalHarmonicPatch.noteHandle(start, key, velocity, device)
        //WoodHitHarmonicPatch.noteHandle(start, key, velocity, device)
      }
    }


    def play(start: Double, reset: Boolean = true): Double = {
      if (reset) client.resetClock()

      println(s"Part 2 start $start")

      var time = start
      0 until 8 foreach {
        _ =>
          val overallAmp = randomRange(0.2, 0.8)
          val cloudLen = middleMetalInstrument.playCloud(time, overallAmp)
          time = time + (cloudLen * randomRange(1.75, 2.0))
      }
      0 until 5 foreach {
        _ =>
          val overallAmp = randomRange(0.2, 0.8)
          val cloudLen = middleHighMetalInstrument.playCloud(time, overallAmp)
          time = time + (cloudLen * randomRange(1.25, 1.5))
      }
      0 until 8 foreach {
        _ =>
          val overallAmp = randomRange(0.2, 0.8)
          val cloudLen = middleMetalInstrument.playCloud(time, overallAmp)
          time = time + (cloudLen * randomRange(1.75, 2.0))
      }
      0 until 3 foreach {
        _ =>
          val overallAmp = randomRange(0.2, 0.8)
          val cloudLen = middleHighMetalInstrument.playCloud(time, overallAmp)
          time = time + (cloudLen * randomRange(1.25, 1.5))
      }
      0 until 2 foreach {
        _ =>
          val overallAmp = randomRange(0.2, 0.8)
          val cloudLen = middleMetalInstrument.playCloud(time, overallAmp)
          time = time + (cloudLen * randomRange(1.75, 2.0))
      }

      var nextStart = start + randomRange(21, 34)
      nextStart = playHarmonicVariant1(nextStart, randomRange(0.5, 0.75), 0)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.25, 1.50), 1)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.5, 0.75), 0)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.25, 1.50), 3)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.5, 0.75), 5)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.25, 1.50), 2)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.5, 0.75), 4)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.25, 1.50), 1)

      nextStart = nextStart + randomRange(13, 21)
      nextStart = playHarmonicVariant2(nextStart, randomRange(0.5, 0.75), 0)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.25, 1.50), 1)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.5, 0.75), 0)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.25, 1.50), 3)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.5, 0.75), 5)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.25, 1.50), 2)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.5, 0.75), 4)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.25, 1.50), 1)

      nextStart = nextStart + randomRange(8, 13)
      Part3Patch.middleStoneInstrument.playCloud(nextStart, randomRange(0.2, 0.8))

      println(s"Part 2 dust time $time harmony time $nextStart")

      time
    }

    def playHarmonicVariant1(start: Double, nextStartFactor: Double, note: Int): Double = {
      println(s"Harmonic variant 1 $start note $note")
      val harmonicVariant = MetalHit1HarmonicVariants.variants(LOW_SOUND)(1)
      val lowHarmonicVariant = MetalHit1HarmonicVariants.variants(HIGH_SOUND).head
      val duration = randomRange(5, 8)
      val amp = randomRange(0.2, 0.8)
      val pan = randomRange(-0.8, 0.8)
      harmonicVariant(
        start, // start
        note,
        amp, // amp
        randomRange(0.33, 0.66), // peak
        duration, //duration
        pan) // pan
      lowHarmonicVariant(
        start + randomRange(-0.4, 1.0), // start
        note,
        amp * randomRange(0.5, 0.7), //amp
        randomRange(0.33, 0.66), // peak
        duration * randomRange(0.8, 1), // duration
        pan * randomRange(-0.9, -1.1)) // pan
      start + (duration * nextStartFactor)
    }

    def playHarmonicVariant2(start: Double, nextStartFactor: Double, note: Int): Double = {
      println(s"Harmonic variant 2 $start note $note")
      val harmonicVariant = MetalHit1HarmonicVariants.variants(LOW_SOUND).head
      val middleHarmonicVariant = MetalHit1HarmonicVariants.variants(MIDDLE_SOUND).head
      val duration = randomRange(5, 8)
      val amp = randomRange(0.2, 0.8)
      val pan = randomRange(-0.8, 0.8)

      harmonicVariant(
        start, // start
        note,
        amp, // amp
        randomRange(0.33, 0.66), // peak
        duration, //duration
        pan) // pan
      middleHarmonicVariant(
        start + randomRange(-0.4, 1.0), // start
        note,
        amp, //amp
        randomRange(0.33, 0.66), // peak
        duration * randomRange(0.8, 1), // duration
        pan * randomRange(-0.9, -1.1)) // pan
      start + (duration * nextStartFactor)
    }


  }

  object Part1Patch extends Patch {
    // 1. low wood hit with small percentage low wood scratch
    // 2. middle low wood hit with small percentage middle low wood scratch
    // Repeat lower wood hit random range of 0.55 - 0.85 of the length
    // Repeat middle low hit random range of 0.15 - 0.45 of the length
    // lower hit range with 33 percent chance
    // long melody 0 1 0 3. 5 2 4 1  Sine instrument but double and color with the other instruments


    val lowWoodInstrument = CloudInstrument(
      totalLengthGenerator = WeightedRandom(Seq(
        (() => randomRange(5, 8), 0.6),
        (() => randomRange(8, 13), 0.4))),
      timeGenerator = WeightedRandom(Seq(
        (() => randomRange(0.03, 0.25), 0.95),
        (() => randomRange(0.25, 0.45), 0.05))),
      soundGenerator = WeightedRandom(Seq(
        ((() => WOOD_HIT_MIXES, LOW_SOUND), 0.92),
        ((() => WOOD_SCRATCH_MIXES, LOW_SOUND), 0.08))),
      ampGenerator = (overallAmp, localTime) => overallAmp * (localTime * randomRange(0.3, 0.5) * 7))

    val lowMiddleWoodInstrument = CloudInstrument(
      totalLengthGenerator = WeightedRandom(Seq(
        (() => randomRange(3, 5), 0.6),
        (() => randomRange(5, 8), 0.4))),
      timeGenerator = WeightedRandom(Seq(
        (() => randomRange(0.02, 0.15), 0.90),
        (() => randomRange(0.40, 0.60), 0.10))),
      soundGenerator = WeightedRandom(Seq(
        ((() => WOOD_HIT_MIXES, LOW_MIDDLE_SOUND), 0.9),
        ((() => WOOD_SCRATCH_MIXES, LOW_MIDDLE_SOUND), 0.1))),
      ampGenerator = (overallAmp, localTime) => overallAmp * (localTime * randomRange(0.3, 0.5) * 7))


    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val amp = velocity / 127.0

      device match {
        case "PAD:nanoPAD2" =>
          key match {
            case 36 => lowWoodInstrument.playCloud(start, amp)
            case 37 => lowMiddleWoodInstrument.playCloud(start, amp)
            case _ =>
          }
        case "KEYBOARD:microKEY2" =>
          WoodHarmonicPatch2.noteHandle(start, key, velocity, device)
          //WoodHitHarmonicPatch.noteHandle(start, key, velocity, device)
      }
    }

    val playLowMiddleWood = WeightedRandom(Seq((true, 0.33), (false, 0.66)))

    def play(start: Double, reset: Boolean = true): Double = {
      println(s"Part 1 start $start")
      if(reset) client.resetClock()

      var time = start

      0 until 26 foreach {
        _ =>
          val overallAmp = randomRange(0.2, 0.8)
          val cloudLen = lowWoodInstrument.playCloud(time, overallAmp)
          if(playLowMiddleWood.choose()) {
            val lowMiddleWoodTime = time + (cloudLen * randomRange(0.10, 0.33))
            lowMiddleWoodInstrument.playCloud(lowMiddleWoodTime, overallAmp)
          }
          time = time + (cloudLen * randomRange(0.66, 0.90))
      }

      var nextStart = start + randomRange(13, 21)
      nextStart = playHarmonicVariant1(nextStart, randomRange(0.75, 1.0), 0)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.0, 1.25), 1)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.75, 1.0), 0)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.0, 1.25), 3)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.75, 1.0), 5)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.0, 1.25), 2)

      nextStart = playHarmonicVariant1(nextStart, randomRange(0.75, 1.0), 4)
      nextStart = playHarmonicVariant1(nextStart, randomRange(1.0, 1.25), 1)

      nextStart = nextStart + randomRange(13, 21)
      nextStart = playHarmonicVariant2(nextStart, randomRange(0.75, 1.0), 0)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.0, 1.25), 1)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.75, 1.0), 0)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.0, 1.25), 3)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.75, 1.0), 5)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.0, 1.25), 2)

      nextStart = playHarmonicVariant2(nextStart, randomRange(0.75, 1.0), 4)
      nextStart = playHarmonicVariant2(nextStart, randomRange(1.0, 1.25), 1)

      nextStart = nextStart + randomRange(8, 13)
      Part2Patch.middleHighMetalInstrument.playCloud(nextStart, randomRange(0.2, 0.8))
      println(s"Part 1 dust time $time harmony time $nextStart")
      time

    }

    def playHarmonicVariant1(start: Double, nextStartFactor: Double, note: Int): Double = {
      println(s"Harmonic variant 1 $start note $note")
      val harmonicVariant = WoodHit1HarmonicVariants.variants(LOW_SOUND)(1)
      val duration = randomRange(5, 8)
      harmonicVariant(start, note, randomRange(0.2, 0.8), randomRange(0.33, 0.66), duration, randomRange(-0.8, 0.8))
      start + (duration * nextStartFactor)
    }

    def playHarmonicVariant2(start: Double, nextStartFactor: Double, note: Int): Double = {
      println(s"Harmonic variant 2 $start note $note")
      val harmonicVariant = WoodHit1HarmonicVariants.variants(LOW_SOUND)(1)
      val lowHarmonicVariant = WoodHit1HarmonicVariants.variants(LOW_SOUND).head
      val duration = randomRange(5, 8)
      val amp = randomRange(0.2, 0.8)
      val pan = randomRange(-0.8, 0.8)
      harmonicVariant(
        start, // start
        note,
        amp, // amp
        randomRange(0.33, 0.66), // peak
        duration, //duration
        pan) // pan
      lowHarmonicVariant(
        start + randomRange(-0.4, 1.0), // start
        note,
        amp * randomRange(0.5, 0.7), //amp
        randomRange(0.33, 0.66), // peak
        duration * randomRange(0.8, 1), // duration
        pan * randomRange(-0.9, -1.1)) // pan
      start + (duration * nextStartFactor)
    }
  }

  def playAllParts(start: Double = 0, reset: Boolean = true): Unit = {
    if(reset) client.resetClock()

    val part1Time = Part1Patch.play(start, reset = false)
    val part2start = part1Time + randomRange(5, 8)
    val part2Time = Part2Patch.play(part2start, reset = false)
    val part3start = part2Time + randomRange(5, 8)
    Part3Patch.play(part3start, reset = false)
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
