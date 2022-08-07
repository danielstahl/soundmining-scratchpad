
(
~customSynthDir = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths";
~irDir = PathName.new("/Users/danielstahl/Documents/Projects/soundmining-scratchpad/impulse-response/");

~fftsize = 2048;
~bootFunction = {

	s.loadDirectory(~customSynthDir);
	~irDir.entries.do({
		arg path;
		~loadIr.value(path);
	});
	'Supercollider loaded'.postln;
};

~simpleBootFunction = {
	'Supercollider loaded'.postln;
};

~findMidiDevice = {
	arg srcID;
	var device;
	MIDIClient.sources.do({
		arg item, i;
		if(item.uid == srcID, {device = item});
	});
	device
};

~deviceName = {
	arg device;
	device.name ++ ":" ++ device.device
};

~midiBootFunction = {
	var midiReciever;

	MIDIClient.init;
	MIDIIn.connectAll;
	if(MIDIClient.sources.size > 0,
	{
		'Setting up midi'.postln;

		midiReciever = NetAddr("localhost", 57111);

		MIDIdef.noteOn(\noteOn, {
			arg veloc, num, chan, srcID;
			var device, deviceName;
			device = ~findMidiDevice.value(srcID);
			deviceName = ~deviceName.value(device);
			postf("note on % veloc % chan % device %\n", num, veloc, chan, deviceName);
			midiReciever.sendMsg("/noteOn", num, veloc, deviceName);
		});

		MIDIdef.noteOff(\noteOff, {
			arg veloc, num, chan, srcID;
			var device, deviceName;
			device = ~findMidiDevice.value(srcID);
			deviceName = ~deviceName.value(device);
			midiReciever.sendMsg("/noteOff", num, veloc, deviceName);
		});

		MIDIdef.cc(\cc, {
			// 1 = Modulation wheel
			arg value, control, chan, srcID;
			var device, deviceName;
			device = ~findMidiDevice.value(srcID);
			deviceName = ~deviceName.value(device);
			postf("cc value % control% chan % device %\n", value, control, chan, deviceName);
			midiReciever.sendMsg("/cc", value, control, deviceName);
		});

		MIDIdef.bend(\bend, {
			arg value, chan, srcID;
			var device, deviceName;
			device = ~findMidiDevice.value(srcID);
			deviceName = ~deviceName.value(device);
			midiReciever.sendMsg("/bend", value, deviceName);
		});
	},
	{
		'No midi found'.postln;
	})
};

~loadIr = {
	arg irPath;
	var rawIrBufL, rawIrBufR, bufSizeL, bufSizeR, irSpectrumBufL, irSpectrumBufR;
	rawIrBufL = Buffer.readChannel(s, irPath.fullPath, channels: [0]);
	rawIrBufR = Buffer.readChannel(s, irPath.fullPath, channels: [1]);
	s.sync;
	bufSizeL = PartConv.calcBufSize(~fftsize, rawIrBufL);
	bufSizeR = PartConv.calcBufSize(~fftsize, rawIrBufR);
	irSpectrumBufL = Buffer.alloc(s, bufSizeL, 1);
	irSpectrumBufR = Buffer.alloc(s, bufSizeR, 1);
	irSpectrumBufL.preparePartConv(rawIrBufL, ~fftsize);
	irSpectrumBufR.preparePartConv(rawIrBufR, ~fftsize);
	s.sync;
	rawIrBufL.free;
	rawIrBufR.free;
	(irPath.fileName + ' left ' + irSpectrumBufL.bufnum).postln;
	(irPath.fileName + ' right ' + irSpectrumBufR.bufnum).postln;
};

~setupConfig = {
	arg outDevice, numOutputBusChannels;
	s = Server.local;
	s.options.outDevice = outDevice;
	s.options.numOutputBusChannels = numOutputBusChannels;
	s.options.blockSize = 128;
	s.options.sampleRate = 96000;
	s.options.numBuffers = 2056;
	s.options.memSize = 2.pow(20);
	s.dumpOSC(0);
	s.doWhenBooted({
		~midiBootFunction.value;
	});
};
)

// Ordinary config
(
~setupConfig.value("External Headphones", 2);
)
(
~setupConfig.value("Scarlett 2i2 USB", 2);
)
// Mix config
(
~setupConfig.value("64ch Aggregate", 64);
)
(
~setupConfig.value("64ch Scarlett Aggregate", 64);
)


(
var server, score;

server = Server(\nrt,
    options: ServerOptions.new
	.sampleRate_(96000)
    .numOutputBusChannels_(52)
    .numInputBusChannels_(2));

score = Score.newFromFile("/Users/danielstahl/Documents/Music/Pieces/Concrete Music/Concrete Music 9/sc/concrete-music-9/concreteMusic9.txt");

score.recordNRT(
    outputFilePath: "/Users/danielstahl/Documents/Music/Pieces/Concrete Music/Concrete Music 9/stage/concreteMusic9Score.caf".standardizePath,
    headerFormat: "CAF",
    sampleFormat: "float",
    options: server.options,
	duration: 310,
    action: { "done".postln }
);

server.remove;
)








