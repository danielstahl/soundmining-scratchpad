package net.soundmining

import ConcreteMusic10Common._
import net.soundmining.Generative.{WeightedRandom, randomRange}
import net.soundmining.modular.ModularSynth.{relativePercControl, staticControl}

object WoodScratchSound {
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

  lazy val WOOD_SCRATCH_1_MIXES = SoundMixes(
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

  lazy val WOOD_SCRATCH_2_MIXES = SoundMixes(
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

  lazy val WOOD_SCRATCH_3_MIXES = SoundMixes(
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

  lazy val WOOD_SCRATCH_4_MIXES = SoundMixes(
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

  lazy val WOOD_SCRATCH_MIXES = MaterialMixes(Seq(
    WOOD_SCRATCH_1_MIXES, WOOD_SCRATCH_2_MIXES, WOOD_SCRATCH_3_MIXES, WOOD_SCRATCH_4_MIXES))

  object WoodScratch4HarmonicVariants {
    // 685 (peak), 1320, 2529, 3713, 4956, 6408, 8062, 9716, 12263
    def makeSpectrum(rate: Double, fundamental: Double = 685): Seq[Double] =
      Spectrum.makeSpectrum2(fundamental * rate, SPECTRUM_RATE, 50)

    val SPECTRUM_RATE = WOOD_SCRATCH_4_RATE

    val LOWEST_SPECTRUM = makeSpectrum(math.pow(WOOD_SCRATCH_4_RATE, 3))

    val LOW_SPECTRUM = makeSpectrum(math.pow(WOOD_SCRATCH_4_RATE, 2))

    val MIDDLE_SPECTRUM = makeSpectrum(WOOD_SCRATCH_4_RATE)

    val HIGH_SPECTRUM = makeSpectrum(WOOD_SCRATCH_4_RATE / WOOD_SCRATCH_4_RATE)

    val variants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = Map(
      LOW_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          println(s"spectrum $LOWEST_SPECTRUM")
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
          println(s"spectrum $MIDDLE_SPECTRUM")
          synthPlayer()
            .triangle(staticControl(MIDDLE_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 0)))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 1)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      HIGH_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          println(s"spectrum $HIGH_SPECTRUM")
          synthPlayer()
            .saw(staticControl(HIGH_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(HIGH_SPECTRUM(note + 3)))
            .ring(staticControl(HIGH_SPECTRUM(note + 6)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      )
    )
  }

  object WoodScratch3HarmonicVariants {
    // 661 (peak), 1501, 1996, 2326, 3156, 5215, 9061, 10836

    def makeSpectrum(rate: Double, fundamental: Double = 661): Seq[Double] =
      Spectrum.makeSpectrum2(fundamental * rate, SPECTRUM_RATE, 50)

    val SPECTRUM_RATE = WOOD_SCRATCH_3_RATE

    val LOWEST_SPECTRUM = makeSpectrum(math.pow(WOOD_SCRATCH_3_RATE, 3))

    val LOW_SPECTRUM = makeSpectrum(math.pow(WOOD_SCRATCH_3_RATE, 2))

    val MIDDLE_SPECTRUM = makeSpectrum(WOOD_SCRATCH_3_RATE)

    val HIGH_SPECTRUM = makeSpectrum(WOOD_SCRATCH_3_RATE / WOOD_SCRATCH_3_RATE)

    val variants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = Map(
      LOW_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          println(s"spectrum $LOWEST_SPECTRUM")
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
          println(s"spectrum $MIDDLE_SPECTRUM")
          synthPlayer()
            .triangle(staticControl(MIDDLE_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 0)))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 1)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      HIGH_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          println(s"spectrum $HIGH_SPECTRUM")
          synthPlayer()
            .saw(staticControl(HIGH_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(HIGH_SPECTRUM(note + 3)))
            .ring(staticControl(HIGH_SPECTRUM(note + 6)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      )
    )
  }

  object WoodScratch2HarmonicVariants {
    // 623 (peak), 1467, 3022, 3872, 6188, 8966, 11589
    def makeSpectrum(rate: Double, fundamental: Double = 623): Seq[Double] =
      Spectrum.makeSpectrum2(fundamental * rate, SPECTRUM_RATE, 50)

    val SPECTRUM_RATE = WOOD_SCRATCH_2_RATE

    val LOWEST_SPECTRUM = makeSpectrum(math.pow(WOOD_SCRATCH_2_RATE, 3))

    val LOW_SPECTRUM = makeSpectrum(math.pow(WOOD_SCRATCH_2_RATE, 2))

    val MIDDLE_SPECTRUM = makeSpectrum(WOOD_SCRATCH_2_RATE)

    val HIGH_SPECTRUM = makeSpectrum(WOOD_SCRATCH_2_RATE / WOOD_SCRATCH_2_RATE)

    val variants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = Map(
      LOW_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          println(s"spectrum $LOWEST_SPECTRUM")
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
          println(s"spectrum $MIDDLE_SPECTRUM")
          synthPlayer()
            .triangle(staticControl(MIDDLE_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 0)))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 1)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      HIGH_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          println(s"spectrum $HIGH_SPECTRUM")
          synthPlayer()
            .saw(staticControl(HIGH_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(HIGH_SPECTRUM(note + 3)))
            .ring(staticControl(HIGH_SPECTRUM(note + 6)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      )
    )
  }

  object WoodScratch1HarmonicVariants {
    // 398 (peak), 1472, 2299, 3642, 4495, 7342, 14677
    def makeSpectrum(rate: Double, fundamental: Double = 398): Seq[Double] =
      Spectrum.makeSpectrum2(fundamental * rate, SPECTRUM_RATE, 50)

    val SPECTRUM_RATE = WOOD_SCRATCH_1_RATE

    val LOWEST_SPECTRUM = makeSpectrum(math.pow(WOOD_SCRATCH_1_RATE, 2))

    val LOW_SPECTRUM = makeSpectrum(WOOD_SCRATCH_1_RATE)

    val MIDDLE_SPECTRUM = makeSpectrum(WOOD_SCRATCH_1_RATE)

    val HIGH_SPECTRUM = makeSpectrum(WOOD_SCRATCH_1_RATE / WOOD_SCRATCH_1_RATE)

    val variants: Map[SoundVariant, Seq[(Double, Int, Double, Double, Double, Double) => Unit]] = Map(
      LOW_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          println(s"spectrum $LOWEST_SPECTRUM")
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
          println(s"spectrum $MIDDLE_SPECTRUM")
          synthPlayer()
            .triangle(staticControl(MIDDLE_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 0)))
            .ring(staticControl(MIDDLE_SPECTRUM(note + 1)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      ),
      HIGH_SOUND -> Seq(
        (start, note, amp, attackTime, duration, pan) => {
          println(s"spectrum $HIGH_SPECTRUM")
          synthPlayer()
            .saw(staticControl(HIGH_SPECTRUM(note)), relativePercControl(0.0001, amp, attackTime, Left(Seq(0, 0))))
            .ring(staticControl(HIGH_SPECTRUM(note + 3)))
            .ring(staticControl(HIGH_SPECTRUM(note + 6)))
            .pan(staticControl(pan))
            .playWithDuration(start, duration)
        }
      )
    )
  }
}
