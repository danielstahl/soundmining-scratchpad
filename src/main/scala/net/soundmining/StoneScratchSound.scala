package net.soundmining

import ConcreteMusic10Common._
import net.soundmining.Generative.{WeightedRandom, randomRange}
import net.soundmining.modular.ModularSynth.staticControl

object StoneScratchSound {

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

  lazy val STONE_SCRATCH_1_MIXES = SoundMixes(STONE_SCRATCH_1, STONE_SCRATCH_1_VARIANTS,
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

  lazy val STONE_SCRATCH_2_MIXES = SoundMixes(
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

  lazy val STONE_SCRATCH_3_MIXES = SoundMixes(
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

  lazy val STONE_SCRATCH_4_MIXES = SoundMixes(
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

  lazy val STONE_SCRATCH_MIXES = MaterialMixes(
    Seq(STONE_SCRATCH_1_MIXES, STONE_SCRATCH_2_MIXES, STONE_SCRATCH_3_MIXES, STONE_SCRATCH_4_MIXES))
}
