package net.soundmining

import ConcreteMusic10Common._
import net.soundmining.Generative.{WeightedRandom, randomRange}
import net.soundmining.modular.ModularSynth.staticControl

object MetalScratchSound {
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

  lazy val METAL_SCRATCH_1_MIXES = SoundMixes(
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

  lazy val METAL_SCRATCH_2_MIXES = SoundMixes(
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

  lazy val METAL_SCRATCH_MIXES = MaterialMixes(Seq(METAL_SCRATCH_1_MIXES, METAL_SCRATCH_2_MIXES))
}
