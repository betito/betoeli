package com.smartshuffle.extract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Singaram Subramanian Extract metadata of an audio file using Apache
 *         Tika API
 * 
 */

public class Extractor {

	String audioPath = "";
	String userDir = "";

	public Extractor(String audiopath) {

		this.userDir = System.getProperty("user.dir");
		this.audioPath = audiopath;

		extractMetadata();

	}

	public StringBuilder extractMetadata() {

		StringBuilder metadatastring = null;

		if (!(audioPath.equals(""))) {
			String audioFileLoc = this.userDir + "\\" + this.audioPath;
			metadatastring = new StringBuilder();
			try {

				InputStream input = new FileInputStream(new File(audioFileLoc));
				ContentHandler handler = new DefaultHandler();
				Metadata metadata = new Metadata();
				Parser parser = new Mp3Parser();
				ParseContext parseCtx = new ParseContext();
				parser.parse(input, handler, metadata, parseCtx);
				input.close();

				// List all metadata
				String[] metadataNames = metadata.names();

				for (String name : metadataNames) {
					String tmp = name + ": " + metadata.get(name);
					metadatastring.append(tmp + "\n");
					System.out.println(tmp);

				}

				// Retrieve the necessary info from metadata
				// Names - title, xmpDM:artist etc. - mentioned below may differ
				// based
				// on the standard used for processing and storing standardized
				// and/or
				// proprietary information relating to the contents of a file.

				// System.out.println("Title: " + metadata.get("title"));
				// System.out.println("Artists: " +
				// metadata.get("xmpDM:artist"));
				// System.out.println("Genre: " + metadata.get("xmpDM:genre"));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("No audio file defined!");
		}

		return metadatastring;

	}

}
