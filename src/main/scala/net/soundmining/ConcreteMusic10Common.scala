package net.soundmining

import net.soundmining.Generative.WeightedRandom
import net.soundmining.modular.{SoundPlay, SynthPlayer}
import net.soundmining.synth.SuperColliderClient

import scala.util.Random

object ConcreteMusic10Common {
  implicit val random: Random = new Random()

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

  def pickItems[T](items: Seq[T], size: Int)(implicit random: Random): Seq[T] =
    random.shuffle(items).take(size)

  def randomIntRange(min: Int, max: Int)(implicit random: Random): Int =
    min + random.nextInt((max - min) + 1)

  def makeEvenWeightedRandom[T](values: Seq[T]): WeightedRandom[T] = {
    val rate = 1.0 / (values.size - 1)
    val pairs = values.map {
      value => (value, rate)
    }
    WeightedRandom(pairs)
  }

  case class SoundMixes(sound: String,
                        soundVariants: Map[SoundVariant, Seq[(Double, Double, Double) => Unit]],
                        soundVariantMixes: Map[SoundVariant, Map[SoundVariant, WeightedRandom[Int]]]) {
    def playVariant(soundVariant: SoundVariant, start: Double, amp: Double, pan: Double): Unit = {
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

  implicit val client: SuperColliderClient = SuperColliderClient()
  val synthPlayer = SynthPlayer(soundPlays = soundPlays, numberOfOutputBuses = 2, bufferedPlayback = true)
}
