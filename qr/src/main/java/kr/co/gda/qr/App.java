package kr.co.gda.qr;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class App extends JFrame {
	static Logger log = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws WriterException, IOException {
        System.out.println( "Hello World!" );
        
        // 1. QR에 어떤 컨텐츠(문자열 영문자 4000자 정도)를 추가할 것인가? -> 다른 API를 통해서  획득
        // QRService qrService = new QRService();
        // String name = qrService.getUserName();
        QRService qrService = new QRService();
        String userName = qrService.getUserName();
        String userBirth = qrService.getUserBirth();
        String userGender = qrService.getUserGender();
        
        StringBuffer contents = new StringBuffer();
        contents.append(userName);
        contents.append(", ");
        contents.append(userBirth);
        contents.append(", ");
        contents.append(userGender);
        // contents.append(",");
        
        // 2. QR 생성
        QRCodeWriter qrWriter = new QRCodeWriter();
        // QR 이미지 관련(모양, 데이터: 컨텐츠)
        BitMatrix matrix = qrWriter.encode(contents.toString(), BarcodeFormat.QR_CODE, 200, 200); // toString: 문자로 바꾼다
        // QR 설정(색상)
        MatrixToImageConfig config = new MatrixToImageConfig(0xFFFFFFFF, 0XFF000000); // 색상 0x:16진수, FF: 투명도, FF: R, FF: G, FF: B
        // 두개의 설정 매개변수를 이용하여 이미지 생성
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix, config);
        
        // 3. QR 저장
        String imageFileName = "myqr.png";
        ImageIO.write(qrImage, "png", new File("myqr.png")); // 메모라인의 이미지, 확장자
        
        // 4. QR 출력 -> web이면 jsp view -> pc 앱이면 swing frame -> android면 activity
        App app = new App();
        app.setTitle("QR");
        app.setLayout(new FlowLayout()); // FlowLayout(): 배치관리자, 왼쪽에서 오른쪽으로 배치
        
        ImageIcon icon = new ImageIcon(imageFileName); // ImageIcon: 아이콘을 이미지객체로 만든다
        JLabel imageLabel = new JLabel(icon); // 라벨 설정
        app.add(imageLabel);
        
        app.setSize(200, 200);
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
