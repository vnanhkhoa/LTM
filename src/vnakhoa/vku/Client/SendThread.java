package vnakhoa.vku.Client;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.stream.IIOByteBuffer;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import com.github.sarxos.webcam.Webcam;

import vnakhoa.vku.Model.CallingMessenger;
import vnakhoa.vku.Model.Messenger;

public class SendThread extends Thread{
	
	public Vector<Socket> listClient;
	public Webcam webcam;

	public SendThread(Vector<Socket> listClient, Webcam webcam) {
		super();
		this.listClient = listClient;
		this.webcam = webcam;
		start();
	}

	@Override
	public void run() {
		while (true) {
			if (listClient.size() > 0) {
				listClient.forEach((socket) -> {
					try {
						sendMessage(socket);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		}
	}
	
	public void sendMessage(Socket socket) throws Exception {
        OutputStream ops = socket.getOutputStream();
        ObjectOutputStream ots = new ObjectOutputStream(ops);
        ots.writeObject(new CallingMessenger(IMG(webcam.getImage())));
        ots.flush();
        
    }
	
	private byte [] IMG(Image image) throws Exception {
//		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
//		
//		oo.writeObject(image);
//		oo.close();
//		byte[] sendData = bStream.toByteArray();
//		return sendData;
		File f = new File("icon\\1f44d.png");
		FileInputStream imgg = new FileInputStream(f);
		byte i[] = new byte[(int) f.length()];
		imgg.read(i, 0, (int) f.length());
		return i;
    }
    
}
