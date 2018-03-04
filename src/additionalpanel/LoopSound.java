package additionalpanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class LoopSound {

        private AudioInputStream ais;
        private Clip clip;
        private URL url;

        public void startMusic(String path) throws Exception{
            url = this.getClass().getResource(path);
            clip = AudioSystem.getClip();
            // getAudioInputStream() also accepts a File or InputStream
            ais = AudioSystem.getAudioInputStream(url);
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        public void stopMusic(){
            clip.stop();
        }
}
