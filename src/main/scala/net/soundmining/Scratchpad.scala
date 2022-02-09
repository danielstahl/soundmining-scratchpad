package net.soundmining

import de.sciss.osc.UDP.Receiver
import de.sciss.osc.{Message, Packet, PacketCodec, UDP}
import net.soundmining.modular.SynthPlayer
import net.soundmining.synth.{Instrument, SuperColliderClient}
import net.soundmining.synth.SuperColliderClient.loadDir

import java.net.SocketAddress
import scala.util.Random

object Scratchpad {

  implicit val client: SuperColliderClient = SuperColliderClient()
  val SYNTH_DIR = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths"
  val synthPlayer = SynthPlayer(soundPlays = Map.empty, numberOfOutputBuses = 2)
  var midiServer: Receiver.Undirected = _
  implicit val random: Random = new Random()

  def init(): Unit = {
    println("Starting up SuperCollider client")
    client.start
    Instrument.setupNodes(client)
    client.send(loadDir(SYNTH_DIR))
    synthPlayer.init()


    val cfg = UDP.Config()
    cfg.codec = PacketCodec().doublesAsFloats().booleansAsInts()
    cfg.localPort = 57111
    this.midiServer = UDP.Receiver(cfg)
    this.midiServer.connect()
    this.midiServer.action = midiReply
  }

  def noteHandle(key: Int, velocity: Int): Unit = {
    if (client.clockTime <= 0) client.resetClock
    val start = (System.currentTimeMillis() - (client.clockTime + 1900)) / 1000.0
  }

  def midiReply(packet: Packet, socketAddress: SocketAddress): Unit = {
    packet match {
      case Message("/noteOn", key: Int, velocity: Int) =>
        noteHandle(key, velocity)
      case _ =>
    }
  }

  def stop(): Unit = {
    println("Stopping SuperCollider client")
    client.stop
    this.midiServer.close()
  }
}
