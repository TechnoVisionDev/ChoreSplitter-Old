package data.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import chore.Chore;

/**
 * MongoDB Codec to encode and decode Chore objects with database.
 * @author Thomas Peters
 */
public class ChoreCodec implements Codec<Chore> {

	@Override
	public void encode(BsonWriter writer, Chore chore, EncoderContext encoderContext) {
		writer.writeString("name", chore.getName());
		writer.writeString("description", chore.getDescription());
		writer.writeInt32("points", chore.getPoints());
		writer.writeInt32("time", chore.getCompletionTime());
	}

	@Override
	public Chore decode(BsonReader reader, DecoderContext decoderContext) {
		String name = reader.readString("name");
		String description = reader.readString("description");
		int points = reader.readInt32("points");
		int time = reader.readInt32("time");
		return new Chore(name, description, points, time);
	}
	
	@Override
	public Class<Chore> getEncoderClass() {
		return Chore.class;
	}

}
