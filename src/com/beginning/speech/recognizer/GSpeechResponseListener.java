package com.beginning.speech.recognizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Response listeners for URL connections.
 * @author Skylion
 *
 */
public interface GSpeechResponseListener {

    public void onResponse(GoogleResponse gr) throws IOException, URISyntaxException, SQLException, ClassNotFoundException;

}
