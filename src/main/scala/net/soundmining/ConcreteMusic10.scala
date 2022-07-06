package net.soundmining

import net.soundmining.Generative.{WeightedRandom, randomRange}
import net.soundmining.modular.ModularSynth.staticControl
import net.soundmining.modular.{SoundPlay, SynthPlayer}
import net.soundmining.synth.SuperColliderClient.loadDir
import net.soundmining.synth.{EmptyPatch, Instrument, Patch, PatchPlayback, SuperColliderClient, SuperColliderReceiver}

import scala.annotation.tailrec
import scala.util.Random

object ConcreteMusic10 {
  implicit val client: SuperColliderClient = SuperColliderClient()
  implicit val random: Random = new Random()
  val SYNTH_DIR = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths"
  val SOUNDS_DIR = "/Users/danielstahl/Documents/Music/Pieces/Concrete Music/Concrete Music 10/sounds"

  val METAL_HIT_1 = "metal-hit-1"
  val METAL_HIT_2 = "metal-hit-2"
  val METAL_HITS = Seq(METAL_HIT_1, METAL_HIT_2)
  val METAL_SCRATCH_1 = "metal-scratch-1"
  val METAL_SCRATCH_2 = "metal-scratch-2"
  val METAL_SCRATCHES = Seq(METAL_SCRATCH_1, METAL_SCRATCH_2)
  val STONE_HIT_1 = "stone-hit-1"
  val STONE_HIT_2 = "stone-hit-2"
  val STONE_HITS = Seq(STONE_HIT_1, STONE_HIT_2)
  val STONE_SCRATCH_1 = "stone-scratch-1"
  val STONE_SCRATCH_2 = "stone-scratch-2"
  val STONE_SCRATCH_3 = "stone-scratch-3"
  val STONE_SCRATCH_4 = "stone-scratch-4"
  val STONE_SCRATCHES = Seq(STONE_SCRATCH_1, STONE_SCRATCH_2, STONE_SCRATCH_3, STONE_SCRATCH_4)
  val WOOD_HIT_1 = "wood-hit-1"
  val WOOD_HIT_2 = "wood-hit-2"
  val WOOD_HITS = Seq(WOOD_HIT_1, WOOD_HIT_2)
  val WOOD_SCRATCH_1 = "wood-scratch-1"
  val WOOD_SCRATCH_2 = "wood-scratch-2"
  val WOOD_SCRATCH_3 = "wood-scratch-3"
  val WOOD_SCRATCH_4 = "wood-scratch-4"
  val WOOD_SCRATCHES = Seq(WOOD_SCRATCH_1, WOOD_SCRATCH_2, WOOD_SCRATCH_3, WOOD_SCRATCH_4)

  sealed trait SoundVariant {
    override def toString: String = {
      this.getClass.getSimpleName.replace("$", "")
    }
  }

  object CLEAN_SOUND extends SoundVariant
  object LOW_SOUND extends SoundVariant
  object LOW_MIDDLE_SOUND extends SoundVariant
  object MIDDLE_SOUND extends SoundVariant
  object MIDDLE_HIGH_SOUND extends SoundVariant
  object HIGH_SOUND extends SoundVariant

  val soundPlays: Map[String, SoundPlay] = Map(
    // 148, 647, 1569 (peak), 3420, 4658, 8774
    METAL_HIT_1 -> SoundPlay(s"$SOUNDS_DIR/Metal hit 1.flac", 0.394, 0.997),
    // 184, 661, 1076 (peak), 4381, 6431, 11309
    METAL_HIT_2 -> SoundPlay(s"$SOUNDS_DIR/Metal hit 2.flac", 0.416, 0.995),
    // 808, 1263, 1953 (peak), 2426, 3186, 4272
    METAL_SCRATCH_1 -> SoundPlay(s"$SOUNDS_DIR/Metal scratch 1.flac", 0.052, 0.723),
    // 267, 849, 1674 (peak), 2547, 3230, 4306, 5970, 9328
    METAL_SCRATCH_2 -> SoundPlay(s"$SOUNDS_DIR/Metal scratch 2.flac", 0.145, 0.755),
    // 934 (peak), 1379, 2333, 3461, 6163, 9816
    STONE_HIT_1 -> SoundPlay(s"$SOUNDS_DIR/Stone hit 1.flac", 0.379, 0.658),
    // 928, 1598, 3469 (peak), 6157, 7025, 8317, 9852
    STONE_HIT_2 -> SoundPlay(s"$SOUNDS_DIR/Stone hit 2.flac", 0.298, 0.449),
    // 881, 2029 (peak), 3516, 7211, 10002
    STONE_SCRATCH_1 -> SoundPlay(s"$SOUNDS_DIR/Stone scratch 1.flac", 0.084, 0.738),
    // 403, 801, 1883, 3474 (peak), 6262, 7351, 11256
    STONE_SCRATCH_2 -> SoundPlay(s"$SOUNDS_DIR/Stone scratch 2.flac", 0.055, 0.422),
    // 342, 841, 2021, 3504 (peak), 7084, 10980
    STONE_SCRATCH_3 -> SoundPlay(s"$SOUNDS_DIR/Stone scratch 3.flac", 0.067, 0.415),
    // 252, 770, 1003, 1509, 2033, 3463 (peak), 7193, 11203
    STONE_SCRATCH_4 -> SoundPlay(s"$SOUNDS_DIR/Stone scratch 4.flac", 0.094, 0.549),
    // 613 (peak), 1044, 1955, 3052, 4684, 6199
    WOOD_HIT_1 -> SoundPlay(s"$SOUNDS_DIR/Wood hit 1.flac", 0.201, 0.339),
    // 436 (peak), 977, 1443, 2308, 4603, 5312
    WOOD_HIT_2 -> SoundPlay(s"$SOUNDS_DIR/Wood hit 2.flac", 0.175, 0.304),
    // 398 (peak), 1472, 2299, 3642, 4495, 7342, 14677
    WOOD_SCRATCH_1 -> SoundPlay(s"$SOUNDS_DIR/Wood scratch 1.flac", 0.100, 0.579),
    // 623 (peak), 1467, 3022, 3872, 6188, 8966, 11589
    WOOD_SCRATCH_2 -> SoundPlay(s"$SOUNDS_DIR/Wood scratch 2.flac", 0.105, 0.473),
    // 661 (peak), 1501, 1996, 2326, 3156, 5215, 9061, 10836
    WOOD_SCRATCH_3 -> SoundPlay(s"$SOUNDS_DIR/Wood scratch 3.flac", 0.046, 0.530),
    // 685 (peak), 1320, 2529, 3713, 4956, 6408, 8062, 9716, 12263
    WOOD_SCRATCH_4 -> SoundPlay(s"$SOUNDS_DIR/Wood scratch 4.flac", 0.138, 0.559),
  )

  val synthPlayer = SynthPlayer(soundPlays = soundPlays, numberOfOutputBuses = 2)
  val patch = CloudPatch3
  var patchPlayback: PatchPlayback = PatchPlayback(patch = patch, client = client)
  val superColliderReceiver: SuperColliderReceiver = SuperColliderReceiver(patchPlayback)

  def pickItems[T](items: Seq[T], size: Int)(implicit random: Random): Seq[T] =
    random.shuffle(items).take(size)

  def randomIntRange(min: Int, max: Int)(implicit random: Random): Int =
    min + random.nextInt((max - min) + 1)


  case class SoundMixes(sound: String,
                        soundVariants: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]],
                        soundVariantMixes: Map[SoundVariant, Map[SoundVariant, WeightedRandom[Int]]]) {
    def playVariant(soundVariant: SoundVariant, start: Double, amp: Double, pan: Double): Unit = {
      println(s"$soundVariant $sound start $start amp $amp pan $pan")
      soundVariantMixes(soundVariant).foreach {
        case (variant, sizeChooser) =>
          pickItems(soundVariants(variant), sizeChooser.choose())
            .foreach(instr => instr(start, amp, pan))
      }
    }
  }

  case class MaterialMixes(soundMixes: Seq[SoundMixes]) {
    val evenChooseMixes = makeEvenWeightedRandom(soundMixes)

    def playVariant(soundVariant: SoundVariant, start: Double, amp: Double, pan: Double): Unit = {
      evenChooseMixes.choose().playVariant(soundVariant, start, amp, pan)
    }
  }

  // 934 (peak), 1379, 2333, 3461, 6163, 9816
  val STONE_HIT_1_RATE = 934.0 / 1379.0
  val STONE_HIT_1_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 4)
        .lowPass(staticControl(934 * STONE_HIT_1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(934))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(934))
        .lowPass(staticControl(1379))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1379))
        .lowPass(staticControl(2333))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2333))
        .lowPass(staticControl(3461))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3461))
        .lowPass(staticControl(6163))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(6163))
        .lowPass(staticControl(9816))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(9816))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val STONE_HIT_1_MIXES = SoundMixes(STONE_HIT_1, STONE_HIT_1_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.4), (2, 0.6)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2))))))

  // 928, 1598, 3469 (peak), 6157, 7025, 8317, 9852
  val STONE_HIT_2_RATE = 3469.0 / 6157.0
  val STONE_HIT_2_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 4)
        .lowPass(staticControl(928 * STONE_HIT_2_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(928))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(928))
        .lowPass(staticControl(1598))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1598))
        .lowPass(staticControl(3469))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 928, 1598, 3469 (peak), 6157, 7025, 8317, 9852
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3469))
        .lowPass(staticControl(6157))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(6157))
        .lowPass(staticControl(7025))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(7025))
        .lowPass(staticControl(8317))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(8317))
        .lowPass(staticControl(9852))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(9852))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

    val STONE_HIT_2_MIXES = SoundMixes(STONE_HIT_2, STONE_HIT_2_VARIANTS,
      Map(
        LOW_SOUND -> Map(
          LOW_SOUND -> WeightedRandom(Seq((1, 0.7), (2, 0.3)))),
        LOW_MIDDLE_SOUND -> Map(
          LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
          MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.5), (2, 0.3)))),
        MIDDLE_SOUND -> Map(
          MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
        MIDDLE_HIGH_SOUND -> Map(
          MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
          HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
        HIGH_SOUND -> Map(
          HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.5), (3, 0.3), (4, 0.1))))))

  val STONE_HIT_MIXES = MaterialMixes(Seq(STONE_HIT_1_MIXES, STONE_HIT_2_MIXES))

  val STONE_SCRATCH_1_RATE = 2029.0 / 3516.0
  // 881, 2029 (peak), 3516, 7211, 10002
  val STONE_SCRATCH_1_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 4)
        .lowPass(staticControl(881 * STONE_SCRATCH_1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(881))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(881))
        .lowPass(staticControl(2029))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2029))
        .lowPass(staticControl(3516))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3516))
        .lowPass(staticControl(7211))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(7211))
        .lowPass(staticControl(10002))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(10002))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val STONE_SCRATCH_1_MIXES = SoundMixes(STONE_SCRATCH_1, STONE_SCRATCH_1_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.4), (2, 0.4)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1))))))

  val STONE_SCRATCH_2_RATE = 3474.0 / 6262.0
  // 403, 801, 1883, 3474 (peak), 6262, 7351, 11256
  val STONE_SCRATCH_2_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 4)
        .lowPass(staticControl(402 * STONE_SCRATCH_2_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(403))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(403))
        .lowPass(staticControl(801))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(801))
        .lowPass(staticControl(1883))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1883))
        .lowPass(staticControl(3474))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
      HIGH_SOUND -> Seq(
        (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
          .playMono(1.0 + randomRange(-0.001, 0.001), amp)
          .highPass(staticControl(3474))
          .lowPass(staticControl(6262))
          .pan(staticControl(pan + randomRange(-0.1, 0.1)))
          .play(start),
        (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
          .playMono(1.0 + randomRange(-0.001, 0.001), amp)
          .highPass(staticControl(6262))
          .lowPass(staticControl(7351))
          .pan(staticControl(pan + randomRange(-0.1, 0.1)))
          .play(start),
        (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
          .playMono(1.0 + randomRange(-0.001, 0.001), amp)
          .highPass(staticControl(7351))
          .lowPass(staticControl(11256))
          .pan(staticControl(pan + randomRange(-0.1, 0.1)))
          .play(start),
        (start, amp, pan) => synthPlayer(STONE_SCRATCH_2)
          .playMono(1.0 + randomRange(-0.001, 0.001), amp)
          .highPass(staticControl(11256))
          .pan(staticControl(pan + randomRange(-0.1, 0.1)))
          .play(start)))

  val STONE_SCRATCH_2_MIXES = SoundMixes(
    STONE_SCRATCH_2, STONE_SCRATCH_2_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.4), (2, 0.4)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.7), (3, 0.1)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.6), (3, 0.2), (4, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.6), (3, 0.2), (4, 0.1))))))

  val STONE_SCRATCH_3_RATE = 3504.0 / 7084.0
  // 342, 841, 2021, 3504 (peak), 7084, 10980
  val STONE_SCRATCH_3_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 4)
        .lowPass(staticControl(342 * STONE_SCRATCH_3_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(342))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(342))
        .lowPass(staticControl(841))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(841))
        .lowPass(staticControl(2021))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2021))
        .lowPass(staticControl(3504))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 342, 841, 2021, 3504 (peak), 7084, 10980
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3504))
        .lowPass(staticControl(7084))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(7084))
        .lowPass(staticControl(10980))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(10980))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val STONE_SCRATCH_3_MIXES = SoundMixes(
    STONE_SCRATCH_3, STONE_SCRATCH_3_VARIANTS, Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.4), (2, 0.4)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.4), (2, 0.4), (3, 0.2)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2))))))

  val STONE_SCRATCH_4_RATE = 3463.0 / 7193.0
  // 252, 770, 1003, 1509, 2033, 3463 (peak), 7193, 11203
  val STONE_SCRATCH_4_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(252 * STONE_SCRATCH_4_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(252))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(252))
        .lowPass(staticControl(770))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(770))
        .lowPass(staticControl(1003))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1003))
        .lowPass(staticControl(1509))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1509))
        .lowPass(staticControl(2033))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2033))
        .lowPass(staticControl(3463))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 252, 770, 1003, 1509, 2033, 3463 (peak), 7193, 11203
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3463))
        .lowPass(staticControl(7193))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(7193))
        .lowPass(staticControl(11203))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(STONE_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(11203))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val STONE_SCRATCH_4_MIXES = SoundMixes(
    STONE_SCRATCH_4, STONE_SCRATCH_4_VARIANTS, Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.4), (3, 0.3), (4, 0.1)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.4), (1, 0.3), (2, 0.2), (3, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2))))))

  val STONE_SCRATCH_MIXES = MaterialMixes(
    Seq(STONE_SCRATCH_1_MIXES, STONE_SCRATCH_2_MIXES, STONE_SCRATCH_3_MIXES, STONE_SCRATCH_4_MIXES))

  val WOOD_HIT_1_RATE = 613.0 / 1044.0
  // 613 (peak), 1044, 1955, 3052, 4684, 6199
  val WOOD_HIT_1_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 4)
        .lowPass(staticControl(613 * WOOD_HIT_1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 2)
        .lowPass(staticControl(613))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(613))
        .lowPass(staticControl(1044))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1044))
        .lowPass(staticControl(1955))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1955))
        .lowPass(staticControl(3052))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 613 (peak), 1044, 1955, 3052, 4684, 6199
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3052))
        .lowPass(staticControl(4684))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(4684))
        .lowPass(staticControl(6199))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(6199))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val WOOD_HIT_1_MIXES = SoundMixes(
    WOOD_HIT_1, WOOD_HIT_1_VARIANTS, Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2))))))

  val WOOD_HIT_2_RATE = 436.0 / 977.0
  // 436 (peak), 977, 1443, 2308, 4603, 5312
  val WOOD_HIT_2_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 4)
        .lowPass(staticControl(436 * WOOD_HIT_2_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 2)
        .lowPass(staticControl(436))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(436))
        .lowPass(staticControl(977))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(977))
        .lowPass(staticControl(1443))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1443))
        .lowPass(staticControl(2308))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 436 (peak), 977, 1443, 2308, 4603, 5312
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2308))
        .lowPass(staticControl(4603))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(4603))
        .lowPass(staticControl(5312))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(5312))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val WOOD_HIT_2_MIXES = SoundMixes(
    WOOD_HIT_2, WOOD_HIT_2_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2)))),
      MIDDLE_HIGH_SOUND ->  Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.6), (3, 0.2))))))

  val WOOD_HIT_MIXES = MaterialMixes(Seq(WOOD_HIT_1_MIXES, WOOD_HIT_2_MIXES))

  val WOOD_SCRATCH_1_RATE = 398.0 / 1472.0
  // 398 (peak), 1472, 2299, 3642, 4495, 7342, 14677
  val WOOD_SCRATCH_1_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(398 * WOOD_SCRATCH_1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 1)
        .lowPass(staticControl(398))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(398))
        .lowPass(staticControl(1472))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1472))
        .lowPass(staticControl(2299))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2299))
        .lowPass(staticControl(3642))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 398 (peak), 1472, 2299, 3642, 4495, 7342, 14677
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3642))
        .lowPass(staticControl(4495))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(4495))
        .lowPass(staticControl(7342))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(7342))
        .lowPass(staticControl(14677))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(14677))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)))

  val WOOD_SCRATCH_1_MIXES = SoundMixes(
    WOOD_SCRATCH_1, WOOD_SCRATCH_1_VARIANTS, Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.3), (2, 0.5), (3, 0.2)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.6), (3, 0.2), (4, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.6), (3, 0.2), (4, 0.1))))))

  val WOOD_SCRATCH_2_RATE = 623.0 / 1467.0
  // 623 (peak), 1467, 3022, 3872, 6188, 8966, 11589
  val WOOD_SCRATCH_2_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(623 * WOOD_SCRATCH_2_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 1)
        .lowPass(staticControl(623))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(623))
        .lowPass(staticControl(1467))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1467))
        .lowPass(staticControl(3022))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3022))
        .lowPass(staticControl(3872))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 623 (peak), 1467, 3022, 3872, 6188, 8966, 11589
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3872))
        .lowPass(staticControl(6188))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(6188))
        .lowPass(staticControl(8966))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(8966))
        .lowPass(staticControl(11589))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(11589))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)))

  val WOOD_SCRATCH_2_MIXES = SoundMixes(
    WOOD_SCRATCH_2, WOOD_SCRATCH_2_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.3), (2, 0.5), (3, 0.2)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.6), (3, 0.2), (4, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.6), (3, 0.2), (4, 0.1))))))

  val WOOD_SCRATCH_3_RATE = 661.0 / 1501.0
  // 661 (peak), 1501, 1996, 2326, 3156, 5215, 9061, 10836
  val WOOD_SCRATCH_3_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(661 * WOOD_SCRATCH_3_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 1)
        .lowPass(staticControl(661))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(661))
        .lowPass(staticControl(1501))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1501))
        .lowPass(staticControl(1996))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1996))
        .lowPass(staticControl(2326))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2326))
        .lowPass(staticControl(3156))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 661 (peak), 1501, 1996, 2326, 3156, 5215, 9061, 10836
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3156))
        .lowPass(staticControl(5215))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(5215))
        .lowPass(staticControl(9061))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(9061))
        .lowPass(staticControl(10836))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_3)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(10836))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val WOOD_SCRATCH_3_MIXES = SoundMixes(
    WOOD_SCRATCH_3, WOOD_SCRATCH_3_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.4), (3, 0.3), (4, 0.1)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.5), (3, 0.2), (4, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.5), (3, 0.2), (4, 0.1))))))

  val WOOD_SCRATCH_4_RATE = 685.0 / 1320.0
  // 685 (peak), 1320, 2529, 3713, 4956, 6408, 8062, 9716, 12263
  val WOOD_SCRATCH_4_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 3)
        .lowPass(staticControl(685 * WOOD_SCRATCH_4_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 1)
        .lowPass(staticControl(685))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(685))
        .lowPass(staticControl(1320))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1320))
        .lowPass(staticControl(2529))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2529))
        .lowPass(staticControl(3713))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    // 685 (peak), 1320, 2529, 3713, 4956, 6408, 8062, 9716, 12263
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3713))
        .lowPass(staticControl(4956))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(4956))
        .lowPass(staticControl(6408))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(6408))
        .lowPass(staticControl(8062))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(8062))
        .lowPass(staticControl(9716))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(9716))
        .lowPass(staticControl(12263))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(WOOD_SCRATCH_4)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(12263))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val WOOD_SCRATCH_4_MIXES = SoundMixes(
    WOOD_SCRATCH_4, WOOD_SCRATCH_4_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.3), (2, 0.5), (3, 0.2)))),
      MIDDLE_HIGH_SOUND ->Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.3), (3, 0.2), (4, 0.2), (5, 0.1), (6, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.3), (3, 0.2), (4, 0.2), (5, 0.1), (6, 0.1))))))

  val WOOD_SCRATCH_MIXES = MaterialMixes(Seq(
    WOOD_SCRATCH_1_MIXES, WOOD_SCRATCH_2_MIXES, WOOD_SCRATCH_3_MIXES, WOOD_SCRATCH_4_MIXES))

  val METAL_HIT_1_RATE = 1569.0 / 3420.0
  // 148, 647, 1569 (peak), 3420, 4658, 8774
  val METAL_HIT_1_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 80)
        .lowPass(staticControl(148 * METAL_HIT_1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 50)
        .lowPass(staticControl(148))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 5)
        .highPass(staticControl(148))
        .lowPass(staticControl(647))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(647))
        .lowPass(staticControl(1569))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1569))
        .lowPass(staticControl(3420))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3420))
        .lowPass(staticControl(4658))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(4658))
        .lowPass(staticControl(8774))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 5)
        .highPass(staticControl(8774))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)))

  val METAL_HIT_1_MIXES = SoundMixes(
    METAL_HIT_1, METAL_HIT_1_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.3), (2, 0.5), (3, 0.2)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1))))))

  val METAL_HIT_2_RATE = 1076.0 / 4381.0
  // 184, 661, 1076 (peak), 4381, 6431, 11309
  val METAL_HIT_2_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 80)
        .lowPass(staticControl(184 * METAL_HIT_2_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 50)
        .lowPass(staticControl(184))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 5)
        .highPass(staticControl(184))
        .lowPass(staticControl(661))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(661))
        .lowPass(staticControl(1076))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1076))
        .lowPass(staticControl(4381))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(4381))
        .lowPass(staticControl(6431))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(6431))
        .lowPass(staticControl(11309))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_HIT_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 5)
        .highPass(staticControl(11309))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)))

  val METAL_HIT_2_MIXES = SoundMixes(
    METAL_HIT_2, METAL_HIT_2_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.3), (2, 0.5), (3, 0.2)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1))))))

  val METAL_HIT_MIXES = MaterialMixes(Seq(METAL_HIT_1_MIXES, METAL_HIT_2_MIXES))

  val METAL_SCRATCH_1_RATE = 1953.0 / 2426.0
  // 808, 1263, 1953 (peak), 2426, 3186, 4272
  val METAL_SCRATCH_1_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 2)
        .lowPass(staticControl(808 * METAL_SCRATCH_1_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 2)
        .lowPass(staticControl(808))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(808))
        .lowPass(staticControl(1263))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1263))
        .lowPass(staticControl(1953))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1953))
        .lowPass(staticControl(2426))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2426))
        .lowPass(staticControl(3186))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3186))
        .lowPass(staticControl(4272))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_1)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(4272))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)))

  val METAL_SCRATCH_1_MIXES = SoundMixes(
    METAL_SCRATCH_1, METAL_SCRATCH_1_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.3), (2, 0.5), (3, 0.2)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1))))))

  val METAL_SCRATCH_2_RATE = 1674.0 / 2547.0
  // 267, 849, 1674 (peak), 2547, 3230, 4306, 5970, 9328
  val METAL_SCRATCH_2_VARIANTS: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]] = Map(
    CLEAN_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    LOW_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 5)
        .lowPass(staticControl(267 * METAL_SCRATCH_2_RATE))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 10)
        .lowPass(staticControl(267))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    MIDDLE_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(267))
        .lowPass(staticControl(849))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(849))
        .lowPass(staticControl(1674))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(1674))
        .lowPass(staticControl(2547))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(2547))
        .lowPass(staticControl(3230))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start)),
    HIGH_SOUND -> Seq(
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(3230))
        .lowPass(staticControl(4306))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(4306))
        .lowPass(staticControl(5970))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp)
        .highPass(staticControl(5970))
        .lowPass(staticControl(9328))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start),
      (start, amp, pan) => synthPlayer(METAL_SCRATCH_2)
        .playMono(1.0 + randomRange(-0.001, 0.001), amp * 5)
        .highPass(staticControl(9328))
        .pan(staticControl(pan + randomRange(-0.1, 0.1)))
        .play(start))
  )

  val METAL_SCRATCH_2_MIXES = SoundMixes(
    METAL_SCRATCH_2, METAL_SCRATCH_2_VARIANTS,
    Map(
      LOW_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((1, 0.6), (2, 0.4)))),
      LOW_MIDDLE_SOUND -> Map(
        LOW_SOUND -> WeightedRandom(Seq((0, 0.1), (1, 0.6), (2, 0.3))),
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.2), (1, 0.3), (2, 0.4), (3, 0.1)))),
      MIDDLE_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((1, 0.2), (2, 0.5), (3, 0.2), (4, 0.1)))),
      MIDDLE_HIGH_SOUND -> Map(
        MIDDLE_SOUND -> WeightedRandom(Seq((0, 0.5), (1, 0.4), (2, 0.1))),
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.8), (3, 0.1)))),
      HIGH_SOUND -> Map(
        HIGH_SOUND -> WeightedRandom(Seq((1, 0.1), (2, 0.6), (3, 0.2), (4, 0.1))))))

  val METAL_SCRATCH_MIXES = MaterialMixes(Seq(METAL_SCRATCH_1_MIXES, METAL_SCRATCH_2_MIXES))

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

  def makeEvenWeightedRandom[T](values: Seq[T]): WeightedRandom[T] = {
    val rate = 1.0 / (values.size - 1)
    val pairs = values.map {
      value => (value, rate)
    }
    WeightedRandom(pairs)
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
