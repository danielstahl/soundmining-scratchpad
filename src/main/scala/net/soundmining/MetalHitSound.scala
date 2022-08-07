package net.soundmining

import ConcreteMusic10Common._
import net.soundmining.Generative.{WeightedRandom, randomRange}
import net.soundmining.modular.ModularSynth.{relativePercControl, staticControl}

object MetalHitSound {
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

  lazy val METAL_HIT_1_MIXES = SoundMixes(
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

  lazy val METAL_HIT_2_MIXES = SoundMixes(
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

  lazy val METAL_HIT_MIXES = MaterialMixes(Seq(METAL_HIT_1_MIXES, METAL_HIT_2_MIXES))



  // 148, 647, 1569 (peak), 3420, 4658, 8774
  object MetalHit1HarmonicVariants {

    def makeSpectrum(rate: Double, fundamental: Double = 647): Seq[Double] =
      Spectrum.makeSpectrum2(fundamental * rate, SPECTRUM_RATE, 50)

    val SPECTRUM_RATE = math.pow(METAL_HIT_1_RATE, 1)

    val LOWEST_SPECTRUM = makeSpectrum(math.pow(METAL_HIT_1_RATE, 3))

    val LOW_SPECTRUM = makeSpectrum(math.pow(METAL_HIT_1_RATE, 2))

    val MIDDLE_SPECTRUM = makeSpectrum(math.pow(METAL_HIT_1_RATE, 1))

    val HIGH_SPECTRUM = makeSpectrum(METAL_HIT_1_RATE)

    val variants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = Map(
      LOW_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .pulse(staticControl(LOWEST_SPECTRUM(note)), relativePercControl(0.0001, amp * 3, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(LOWEST_SPECTRUM(note + 7)))
            .ring(staticControl(LOWEST_SPECTRUM(note + 8)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        },
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .sine(staticControl(LOW_SPECTRUM(note)), relativePercControl(0.0001, amp * 3, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(LOW_SPECTRUM(note + 1)))
            .ring(staticControl(LOW_SPECTRUM(note + 2)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      MIDDLE_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .saw(staticControl(MIDDLE_SPECTRUM(note)), relativePercControl(0.0001, amp * 3, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 11)))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 12)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      HIGH_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .triangle(staticControl(HIGH_SPECTRUM(note)), relativePercControl(0.0001, amp * 3, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(HIGH_SPECTRUM(note + 13)))
            .ring(staticControl(HIGH_SPECTRUM(note + 14)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      )
    )
  }

  // 184, 661, 1076 (peak), 4381, 6431, 11309
  object MetalHit2HarmonicVariants {
    def makeSpectrum(rate: Double, fundamental: Double = 1075): Seq[Double] =
      Spectrum.makeSpectrum2(fundamental * rate, SPECTRUM_RATE, 50)

    val SPECTRUM_RATE = math.pow(METAL_HIT_2_RATE, 2)

    val LOWEST_SPECTRUM = makeSpectrum(math.pow(METAL_HIT_2_RATE, 1))

    val LOW_SPECTRUM = makeSpectrum(math.pow(METAL_HIT_2_RATE, 1))

    val MIDDLE_SPECTRUM = makeSpectrum(METAL_HIT_2_RATE)

    val HIGH_SPECTRUM = makeSpectrum(METAL_HIT_2_RATE)

    val variants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = Map(
      LOW_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .pulse(staticControl(LOWEST_SPECTRUM(note)), relativePercControl(0.0001, amp * 3, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(LOWEST_SPECTRUM(note + 0)))
            .ring(staticControl(LOWEST_SPECTRUM(note + 1)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        },
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .sine(staticControl(LOW_SPECTRUM(note)), relativePercControl(0.0001, amp * 3, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(LOW_SPECTRUM(note + 1)))
            .ring(staticControl(LOW_SPECTRUM(note + 2)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      MIDDLE_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .saw(staticControl(MIDDLE_SPECTRUM(note)), relativePercControl(0.0001, amp * 3, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 10)))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 11)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      HIGH_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          synthPlayer()
            .triangle(staticControl(HIGH_SPECTRUM(note)), relativePercControl(0.0001, amp * 3, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(HIGH_SPECTRUM(note + 15)))
            .ring(staticControl(HIGH_SPECTRUM(note + 16)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      )
    )
  }
}
