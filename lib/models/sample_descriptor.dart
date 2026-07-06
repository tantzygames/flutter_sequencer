import 'dart:math';

double get12TETFrequency(int noteNumber, [a4Frequency = 440.0]) {
  return a4Frequency * pow(2.0, (noteNumber.roundToDouble() - 69.0) / 12.0);
}

/// Contains information about a sample file. Gets converted to AudioKit
/// Sampler's AKSampleDescriptor.
class SampleDescriptor {
  SampleDescriptor({
    required this.filename,
    required this.isAsset,
    required this.noteNumber,
    this.noteFrequency = 0,
    this.minimumNoteNumber = 0,
    this.maximumNoteNumber = 127,
    this.minimumVelocity = 0,
    this.maximumVelocity = 0,
    this.isLooping = false,
    this.loopStartPoint = 0,
    this.loopEndPoint = 0,
    this.startPoint = 0,
    this.endPoint = 0,
  }) {
    noteFrequency = get12TETFrequency(noteNumber);
  }

  String filename;
  bool isAsset;

  // AKSampleDescriptor properties
  int noteNumber;
  double noteFrequency;

  int minimumNoteNumber, maximumNoteNumber;
  int minimumVelocity, maximumVelocity;

  late bool isLooping;
  double loopStartPoint, loopEndPoint;
  double startPoint, endPoint;
}
