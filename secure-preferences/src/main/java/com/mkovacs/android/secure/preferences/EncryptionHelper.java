package com.mkovacs.android.secure.preferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.gmu.tec.scout.utilities.Encryption;

import android.content.SharedPreferences;
import android.util.Base64;

public class EncryptionHelper {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(EncryptionHelper.class);
	private Encryption encryption;

	public EncryptionHelper(Encryption encryption) {
		super();
		this.encryption = encryption;
	}

	@SuppressWarnings("unchecked")
	public <T> T readAndDecodeTemplate(SharedPreferences prefs, String key,
			T defValue) {
		T result = defValue;
		ObjectInputStream ois = readDecoded(prefs, key);
		if (ois != null) {
			try {
				result = (T) ois.readObject();
			} catch (IOException e) {
				LOGGER.error("Error reading value by key: {}", key, e);
			} catch (ClassNotFoundException e) {
				LOGGER.error("Error reading value by key: {}", key, e);
			}
		}
		return result;
	}
	
	public <T> String encode(T value) {
		String result = null;
		if (value != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(value);
				byte[] byteArray = baos.toByteArray();
				byte[] encrypt = encryption.encrypt(byteArray);
				result = Base64.encodeToString(encrypt, Base64.DEFAULT);
			} catch (Exception e) {
				LOGGER.error("Error encoding value", e);
			}
		}
		return result;
	}

	private ObjectInputStream readDecoded(SharedPreferences prefs, String key) {
		String stringValue = prefs.getString(key, null);
		ObjectInputStream result;
		if (stringValue != null) {
			try {
				result = createDecodedObjectStream(stringValue);
			} catch (IOException e) {
				LOGGER.error("Error reading from properties. Key: {}", key, e);
				result = null;
			} catch (Exception e) {
				LOGGER.error("Error decoding value. Key: {}", key, e);
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}

	private ObjectInputStream createDecodedObjectStream(String stringValue)
			throws Exception, IOException {
		byte[] decodedBytes = Base64.decode(stringValue, Base64.DEFAULT);
		byte[] decoded = encryption.decrypt(decodedBytes);
		return new ObjectInputStream(new ByteArrayInputStream(decoded));
	}
}
