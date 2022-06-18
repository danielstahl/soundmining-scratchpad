package net.soundmining

import net.soundmining.modular.ModularSynth.staticControl
import net.soundmining.modular.{SoundPlay, SynthPlayer}
import net.soundmining.synth.SuperColliderClient.loadDir
import net.soundmining.synth.{EmptyPatch, Instrument, Patch, PatchPlayback, SuperColliderClient, SuperColliderReceiver}

import scala.util.Random

object ConcreteMusic10 {
  implicit val client: SuperColliderClient = SuperColliderClient()
  implicit val random: Random = new Random()
  val SYNTH_DIR = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths"
  val SOUNDS_DIR = "/Users/danielstahl/Documents/Music/Pieces/Concrete Music/Concrete Music 10/sounds"

  val METAL_HIT_1 = "metal-hit-1"
  val METAL_HIT_2 = "metal-hit-2"
  val METAL_SCRATCH_1 = "metal-scratch-1"
  val METAL_SCRATCH_2 = "metal-scratch-2"
  val STONE_HIT_1 = "stone-hit-1"
  val STONE_HIT_2 = "stone-hit-2"
  val STONE_SCRATCH_1 = "stone-scratch-1"
  val STONE_SCRATCH_2 = "stone-scratch-2"
  val STONE_SCRATCH_3 = "stone-scratch-3"
  val STONE_SCRATCH_4 = "stone-scratch-4"
  val WOOD_HIT_1 = "wood-hit-1"
  val WOOD_HIT_2 = "wood-hit-2"
  val WOOD_SCRATCH_1 = "wood-scratch-1"
  val WOOD_SCRATCH_2 = "wood-scratch-2"
  val WOOD_SCRATCH_3 = "wood-scratch-3"
  val WOOD_SCRATCH_4 = "wood-scratch-4"

  val soundPlays: Map[String, SoundPlay] = Map(
    METAL_HIT_1 -> SoundPlay(s"$SOUNDS_DIR/Metal hit 1.flac", 0.394, 0.997),
    METAL_HIT_2 -> SoundPlay(s"$SOUNDS_DIR/Metal hit 2.flac", 0.416, 0.995),
    METAL_SCRATCH_1 -> SoundPlay(s"$SOUNDS_DIR/Metal scratch 1.flac", 0.052, 0.723),
    METAL_SCRATCH_2 -> SoundPlay(s"$SOUNDS_DIR/Metal scratch 2.flac", 0.145, 0.755),
    STONE_HIT_1 -> SoundPlay(s"$SOUNDS_DIR/Stone hit 1.flac", 0.379, 0.658),
    STONE_HIT_2 -> SoundPlay(s"$SOUNDS_DIR/Stone hit 2.flac", 0.298, 0.449),
    STONE_SCRATCH_1 -> SoundPlay(s"$SOUNDS_DIR/Stone scratch 1.flac", 0.084, 0.738),
    STONE_SCRATCH_2 -> SoundPlay(s"$SOUNDS_DIR/Stone scratch 2.flac", 0.055, 0.422),
    STONE_SCRATCH_3 -> SoundPlay(s"$SOUNDS_DIR/Stone scratch 3.flac", 0.067, 0.415),
    STONE_SCRATCH_4 -> SoundPlay(s"$SOUNDS_DIR/Stone scratch 4.flac", 0.094, 0.549),
    WOOD_HIT_1 -> SoundPlay(s"$SOUNDS_DIR/Wood hit 1.flac", 0.201, 0.339),
    WOOD_HIT_2 -> SoundPlay(s"$SOUNDS_DIR/Wood hit 2.flac", 0.175, 0.304),
    WOOD_SCRATCH_1 -> SoundPlay(s"$SOUNDS_DIR/Wood scratch 1.flac", 0.100, 0.579),
    WOOD_SCRATCH_2 -> SoundPlay(s"$SOUNDS_DIR/Wood scratch 2.flac", 0.105, 0.473),
    WOOD_SCRATCH_3 -> SoundPlay(s"$SOUNDS_DIR/Wood scratch 3.flac", 0.046, 0.530),
    WOOD_SCRATCH_4 -> SoundPlay(s"$SOUNDS_DIR/Wood scratch 4.flac", 0.138, 0.559),
  )

  val synthPlayer = SynthPlayer(soundPlays = soundPlays, numberOfOutputBuses = 2)
  val patch = SoundPlayback
  var patchPlayback: PatchPlayback = PatchPlayback(patch = patch, client = client)
  val superColliderReceiver: SuperColliderReceiver = SuperColliderReceiver(patchPlayback)

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
