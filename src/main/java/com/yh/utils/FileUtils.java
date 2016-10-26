package com.yh.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.log4j.Logger;


public class FileUtils extends Object {
    static Logger logger = Logger.getLogger(FileUtils.class.getName());

    private String currentRecord = null; //保存文本的变量
    private BufferedReader file; //BufferedReader对象，用于读取文件数据
    private String path; //文件完整路径名
    public FileUtils() {
    }

    // ReadFile方法用来读取文件filePath中的数据，并返回这个数据
    public String ReadFile(String filePath) throws FileNotFoundException {
        path = filePath;
        // 创建新的BufferedReader对象
        file = new BufferedReader(new FileReader(path));
        String returnStr = null;
        try {
            // 读取一行数据并保存到currentRecord变量中
            currentRecord = file.readLine();
        } catch (IOException e) { //错误处理
            System.out.println("读取数据错误.");
        }
        finally {
            try {
                file.close();
            }
            catch (Exception e) {

            }
        }
        if (currentRecord == null)
            //如果文件为空
            returnStr = "没有任何记录";
        else { //文件不为空
            returnStr = currentRecord;
        }
        //返回读取文件的数据
        return returnStr;
    }

    /**
     * 非utf-8的方式
     * @param filePath String
     * @param str String
     * @throws FileNotFoundException
     */
    public void WriteFile(String filePath, String str) throws
            FileNotFoundException {
        path = filePath;
        try {
            // 创建PrintWriter对象，用于写入数据到文件中
            PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
            //用文本格式打印整数Writestr
            pw.println(str);
            //清除PrintWriter对象
            pw.close();
        } catch (IOException e) {
            //错误处理
            System.out.println("写入文件错误" + e.getMessage());
        }
    }

    public void WriteFile(String filefullpath, String str, String charset) {
        try {
            FileOutputStream fo = new FileOutputStream(filefullpath);
            OutputStreamWriter osw = new OutputStreamWriter(fo, charset);
            PrintWriter out1 = new PrintWriter(osw);
            out1.println(str);
            out1.close();
            osw.close();
            fo.close();
        } catch (IOException e) {
            logger.error("writeFile:" + e.getMessage());
        }
    }

    /**
     * 以utf-8编码的方式写文件
     * @param filefullpath String
     * @param str String
     */
    public void WriteFileUTF8(String filefullpath, String str) {
        WriteFile(filefullpath, str, "UTF-8");
    }

    public static boolean CopyFile(String filePathSrc, String filePathDes) {
        boolean re = false;
        File fSrc = new File(filePathSrc);
        if (!fSrc.exists())
            return false;
        try {
            if (fSrc.isFile()) {
                FileInputStream input = new FileInputStream(fSrc);
                FileOutputStream output = new FileOutputStream(filePathDes);
                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = input.read(b)) != -1) {
                    output.write(b, 0, len);
                }
                output.flush();
                output.close();
                input.close();
                re = true;
            } else
                System.out.print("debug:" + filePathSrc + "已不存在！");
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return re;
  }

  public static boolean AppendFile(String desFilePath, String srcFilePath) {
      boolean re = true;
      try {
          RandomAccessFile rf = new RandomAccessFile(desFilePath, "rw");
          // 定义一个类RandomAccessFile的对象，并实例化
          rf.seek(rf.length()); // 将指针移动到文件末尾
          FileInputStream fis = new FileInputStream(srcFilePath);
          BufferedInputStream bis = new BufferedInputStream(fis); // 读取文件的BufferedRead对象
          byte[] buf = new byte[1024];
          int len = 0;
          int totalNum = 0;
          while ((len=bis.read(buf)) != -1) {
              rf.write(buf, 0, len);
              totalNum += len;
          }
          logger.info("AppendFile:" + srcFilePath + " totalNum=" + totalNum + " len=" + len);
          bis.close();
          fis.close(); // 关闭文件
          rf.close();  // 关闭文件流
      } catch (IOException e) {
          re = false;
          logger.error("AppendFile:" + e.getMessage());
      }
      return re;
  }

  public static String getFileExt(String fileName) {
      // 下面取到的扩展名错误，只有三位，而如html的文件则有四位
      // extName = fileName.substring(fileName.length() - 3, fileName.length()); //扩展名
      if (fileName==null)
          return "";
      int dotindex = fileName.lastIndexOf(".");
      String extName = fileName.substring(dotindex + 1, fileName.length());
      extName = extName.toLowerCase(); // 置为小写
      return extName;
  }

  public static String getFileNameWithoutExt(String fileName) {
      int dotindex = fileName.lastIndexOf(".");
      String fName = fileName.substring(0, dotindex);
      fName = fName.toLowerCase(); //置为小写
      return fName;
  }

  public static void del(String filepath) throws IOException {
      File f = new File(filepath); // 定义文件路径
      if (f.exists() && f.isDirectory()) { // 判断是文件还是目录
          if (f.listFiles().length == 0) { // 若目录下没有文件则直接删除
              f.delete();
          } else { // 若有则把文件放进数组，并判断是否有下级目录
              File delFile[] = f.listFiles();
              int i = f.listFiles().length;
              for (int j = 0; j < i; j++) {
                  if (delFile[j].isDirectory()) {
                      del(delFile[j].getAbsolutePath()); // 递归调用del方法并取得子目录路径
                  }
                  delFile[j].delete(); // 删除文件
              }
          }
          del(filepath); // 递归调用
      }

}
  
  public static String read(String filename) {
		StringBuilder sb = new StringBuilder();
		try {
			FileInputStream in = new FileInputStream(new File(filename).getAbsoluteFile());
			FileChannel channel = in.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			//Charset  charset = Charset.forName("utf-8");
			Charset  charset = Charset.defaultCharset();
			CharsetDecoder decoder = charset.newDecoder();
			CharsetEncoder encoder = charset.newEncoder();
			
			try{
				while(true){
					buffer.clear();
					
					CharBuffer  charBuffer = decoder.decode(buffer);
					ByteBuffer charsetBuffer = encoder.encode(charBuffer);
					int red = channel.read(charsetBuffer);
					if(red == -1)
						break;
					
					byte[] bs = charsetBuffer.array();
					sb.append(new String(bs));
					//System.out.println(new String(bs));
				}
				
				
			}finally{
				in.close();
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
		return sb.toString();
	}
  
  public static void  copyFile(String srcFilename,String destFilename) {
		try {
			FileInputStream in = new FileInputStream(srcFilename);
			FileOutputStream out = new FileOutputStream(destFilename);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			FileChannel inFileChannel = in.getChannel();
			FileChannel outFileChannel = out.getChannel();
			
			try{
				while (true) {
					buffer.clear();
					
					int red = inFileChannel.read(buffer);
					
					if(red == -1)
						break;
					
					buffer.flip();
					
					outFileChannel.write(buffer);
					
				}
			}finally{
				in.close();
				out.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
  
}
