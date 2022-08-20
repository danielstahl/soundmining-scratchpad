package net.soundmining

import net.soundmining.modular.ModularSynth.{percControl, relativePercControl, staticControl}
import net.soundmining.modular.SynthPlayer
import net.soundmining.synth.MidiUtils.midiToFreq
import net.soundmining.synth.{EmptyPatch, Instrument, Patch, PatchPlayback, SuperColliderClient, SuperColliderReceiver}
import net.soundmining.synth.SuperColliderClient.loadDir

import scala.util.Random

/*
* Different things
*
* Patch system. Where you can associate and play a "patch" via keyboard.
*
* Make a UI which can control aspects of the sound. Write a UI in Supercollider
* and communicate with it via osc (much like the midi).
*
* The UI could be generic and you could control and assign things from
* the "patch" in scala and set it up via osc.
*
* Also have some useful stuff such as meters and a clock in the there.
* Also be able to send and visualize different notes. With time.
* */
object Scratchpad {

  implicit val client: SuperColliderClient = SuperColliderClient()
  val SYNTH_DIR = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths"
  val synthPlayer = SynthPlayer(soundPlays = Map.empty, numberOfOutputBuses = 2, bufferedPlayback = false)
  val patchChooser = PatchChooser()
  var patchPlayback: PatchPlayback = PatchPlayback(patch = patchChooser, client = client)
  val superColliderReceiver: SuperColliderReceiver = SuperColliderReceiver(patchPlayback)

  implicit val random: Random = new Random()

  def init(): Unit = {
    println("Starting up SuperCollider client")
    client.start
    Instrument.setupNodes(client)
    client.send(loadDir(SYNTH_DIR))
    synthPlayer.init()
    superColliderReceiver.start()

    this.patchChooser.patchChooser = Map(
      36 -> SawPatch,
      37 -> WhiteNoisePluck,
      38 -> PinkNoisePluck,
      39 -> DustPatch,
      40 -> BankPatch)
  }

  val OVERALL_AMP = 4

  def randomRange(min: Double, max: Double)(implicit random: Random): Double =
    min + (max - min) * random.nextDouble()

  def makeAttackCurve(min: Double, max: Double)(implicit random: Random): Seq[Double] = {
    val curve = randomRange(min, max)
    Seq(curve, curve * -1)
  }

  /**
   * Nice fat bass. Warm
   */
  object SawPatch extends Patch {
    def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val pitch = midiToFreq(key)
      val amp = (velocity / 127.0) * OVERALL_AMP
      println(s"start $start key $key freq $pitch velocity $velocity")

      synthPlayer()
        .saw(staticControl(pitch), relativePercControl(0.001, amp, 0.1, Left(Seq(-4, -4))))
        .lowPass(staticControl(pitch * 1))
        .pan(staticControl(0))
        .playWithDuration(start, 3)

      synthPlayer()
        .saw(staticControl(pitch * 1.01), relativePercControl(0.001, amp, 0.1, Left(Seq(-4, -4))))
        .highPass(staticControl(pitch * 5))
        .am(staticControl(pitch * 2))
        .pan(staticControl(0))
        .playWithDuration(start, 3)
    }
  }

  /*
  * Much sharper with white noise. Slight detune of the filtered
  * versions make it more shimmering
  * */
  object WhiteNoisePluck extends Patch {
    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val pitch = midiToFreq(key)
      val amp = (velocity / 127.0) * OVERALL_AMP
      val duration = 5.0

      println(s"start $start key $key freq $pitch velocity $velocity")

      synthPlayer()
        .whiteNoise(percControl(0, amp, 0.01, Left(Seq(-4, -4))), Some(0.001))
        .lowPass(staticControl(pitch / 2))
        .delay(1.0 / ((pitch / 2.0) * 1.01), duration, staticControl(1))
        .pan(staticControl(0.1))
        .playWithDuration(start, duration)

      synthPlayer()
        .whiteNoise(percControl(0, amp, 0.01, Left(Seq(-4, -4))), Some(0.001))
        .highPass(staticControl(pitch))
        .delay(1.0 / (pitch * 0.99), duration, staticControl(1))
        .pan(staticControl(-0.1))
        .playWithDuration(start, duration)

      synthPlayer()
        .whiteNoise(percControl(0, amp, 0.01, Left(Seq(-4, -4))), Some(0.001))
        .delay(1.0 / pitch, duration, staticControl(1))
        .pan(staticControl(0))
        .playWithDuration(start, duration)
    }
  }

  /*
  * Pink noise make it more mellow
  * */
  object PinkNoisePluck extends Patch {
    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val pitch = midiToFreq(key)
      val amp = (velocity / 127.0) * OVERALL_AMP
      println(s"start $start key $key freq $pitch velocity $velocity")
      val duration = 5.0

      synthPlayer()
        .pinkNoise(percControl(0, amp, 0.01, Left(Seq(-4, -4))), Some(0.001))
        .lowPass(staticControl(pitch / 2))
        .delay(1.0 / ((pitch / 2.0) * 1.01), duration, staticControl(1))
        .pan(staticControl(0.1))
        .playWithDuration(start, duration)

      synthPlayer()
        .pinkNoise(percControl(0, amp, 0.01, Left(Seq(-4, -4))), Some(0.001))
        .highPass(staticControl(pitch))
        .delay(1.0 / (pitch * 0.99), duration, staticControl(1))
        .pan(staticControl(-0.1))
        .playWithDuration(start, duration)

      synthPlayer()
        .pinkNoise(percControl(0, amp, 0.01, Left(Seq(-4, -4))), Some(0.001))
        .delay(1.0 / pitch, duration, staticControl(1))
        .pan(staticControl(0))
        .playWithDuration(start, duration)
    }
  }

  object DustPatch extends Patch {

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val pitch = midiToFreq(key)
      val amp = (velocity / 127.0) * OVERALL_AMP
      println(s"start $start key $key freq $pitch velocity $velocity")
      val duration = 13.0

      synthPlayer()
        .dust(percControl(0, amp, 0.5, Left(Seq(-4, -4))),
          staticControl(500))
        .lowPass(staticControl(pitch * 4))
        .pan(staticControl(0))
        .playWithDuration(start, duration)
    }
  }

  object BankPatch extends Patch {
    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      val pitch = midiToFreq(key)
      val amp = (velocity / 127.0) * 0.007
      val duration = 8
      synthPlayer()
        .pinkNoise(percControl(0, amp, 0.5, Left(Seq(-4, -4))))
        .bankOfResonators(Seq(pitch * 6, pitch * 7), Seq(1, 1), Seq(5.1, 5.9))
        .pan(staticControl(0))
        .playWithDuration(start, duration)
    }
  }

  case class PatchChooser(chooseDevice: String = "PAD:nanoPAD2", playDevice: String = "KEYBOARD:microKEY2", var patchChooser: Map[Int, Patch] = Map()) extends Patch {
    private var currentPatch: Patch = EmptyPatch

    override def noteHandle(start: Double, key: Int, velocity: Int, device: String): Unit = {
      device match {
        case device if device == chooseDevice =>
          patchChooser.get(key) match {
            case Some(patch) => {
              currentPatch = patch
              println(s"patch is $key ${currentPatch.getClass.getSimpleName.replace("$", "")}")
            }
            case None => println(s"$key is not mapped to a patch")
          }
        case device if device == playDevice =>
          currentPatch.noteHandle(start, key, velocity, device)
        case _ =>
      }
    }
  }

  def stop(): Unit = {
    println("Stopping SuperCollider client")
    client.stop
    this.superColliderReceiver.stop()
  }
}
