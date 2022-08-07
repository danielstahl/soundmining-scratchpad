package net.soundmining

import ConcreteMusic10Common._
import net.soundmining.Generative.{WeightedRandom, randomRange}
import net.soundmining.modular.ModularSynth.{relativePercControl, staticControl}

object StoneHitSound {


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

  lazy val STONE_HIT_1_MIXES = SoundMixes(STONE_HIT_1, STONE_HIT_1_VARIANTS,
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

  lazy val STONE_HIT_2_MIXES = SoundMixes(STONE_HIT_2, STONE_HIT_2_VARIANTS,
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

  lazy val STONE_HIT_MIXES = MaterialMixes(Seq(STONE_HIT_1_MIXES, STONE_HIT_2_MIXES))



  // 934 (peak), 1379, 2333, 3461, 6163, 9816
  object StoneHit1HarmonicVariants {

    // First LOW + MIDDLE
    // Second LOWEST + HIGH
    def makeSpectrum(rate: Double, fundamental: Double = 934): Seq[Double] =
      Spectrum.makeSpectrum2(fundamental * rate, SPECTRUM_RATE, 50)

    val SPECTRUM_RATE = math.pow(STONE_HIT_1_RATE, 5)

    val LOWEST_SPECTRUM = makeSpectrum(math.pow(STONE_HIT_1_RATE, 4))

    val LOW_SPECTRUM = makeSpectrum(math.pow(STONE_HIT_1_RATE, 3))

    val MIDDLE_SPECTRUM = makeSpectrum(math.pow(STONE_HIT_1_RATE, 2))

    val HIGH_SPECTRUM = makeSpectrum(STONE_HIT_1_RATE)

    val variants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = Map(
      LOW_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .pulse(staticControl(LOWEST_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(LOWEST_SPECTRUM(note + 2)))
            .ring(staticControl(LOWEST_SPECTRUM(note + 4)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        },
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .sine(staticControl(LOW_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(LOW_SPECTRUM(note + 1)))
            .ring(staticControl(LOW_SPECTRUM(note + 2)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      MIDDLE_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .saw(staticControl(MIDDLE_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 0)))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 1)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      HIGH_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .triangle(staticControl(HIGH_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(HIGH_SPECTRUM(note + 3)))
            .ring(staticControl(HIGH_SPECTRUM(note + 6)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      )
    )
  }
}
