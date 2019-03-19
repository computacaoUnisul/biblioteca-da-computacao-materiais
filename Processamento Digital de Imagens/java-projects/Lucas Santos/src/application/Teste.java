package application;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Teste{
	
   public static void main( String[] args ){
	   System.out.println(System.getProperty("java.library.path"));
	   System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
      System.out.println( "mat = " + mat.dump() );
   }
}