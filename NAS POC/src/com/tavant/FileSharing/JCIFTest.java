package com.tavant.FileSharing;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class JCIFTest {
	static final String NETWORK_FOLDER = "smb://dddd";
	NtlmPasswordAuthentication auth;

	public JCIFTest() {
		setAuth(new NtlmPasswordAuthentication("DM0001", "username", "password"));
	}

	public NtlmPasswordAuthentication getAuth() {
		return auth;
	}

	public void setAuth(NtlmPasswordAuthentication auth) {
		this.auth = auth;
	}

	public static void main(String args[]) {
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ApplicationLoad><Header><Datetime>Tue Mar 01 15:26:59 IST 2016</Datetime><Correlationid>52142</Correlationid><SourceSystemName>MB2C</SourceSystemName></Header><Applications><batchId>1</batchId><Application><applId>5587</applId><LOAN_APPLICATION><_DATA_INFORMATION><DATA_VERSION _Name=\"Date\" _Number=\"20160301\"/><DATA_VERSION _Name=\"MISMO\" _Number=\"2.3.1\"/>"
				+ "</_DATA_INFORMATION><LOAN_PURPOSE _Type=\"REFI_CASH_OUT\" PropertyUsageType=\"INVESTMENT\"/><PROPERTY _State=\"IN\" propertyType=\"Land Only\"/>"
				+ "<TRANSACTION_DETAIL/><BORROWER BorrowerID=\"ID_5336\" _FirstName=\"Meenakshi\" _LastName=\"M\" borrowerIndex=\"1\" _PrintPositionType=\"Borrower\">"
				+ "<CONTACT_POINT _PreferenceIndicator=\"Y\" _Type=\"Home Phone\" _Value=\"5135348597\"/><CONTACT_POINT _Type=\"Email Address\" "
				+ "_Value=\"ffff\"/></BORROWER><LOAN_APPLICATION_EXTENSION desiredLoanAmt=\"1215000\""
				+ " applicationsource=\"DII\" channel=\"DI\" sourceBusiness=\"INTRA\" ChannelCode=\"DIRECT\" RegulatoryFinanceType=\"1\" appFlowType=\"LEAD\"/>"
				+ "</LOAN_APPLICATION></Application></Applications></ApplicationLoad>";

		new JCIFTest().copyFiles(content, "testfile1");
	}

	public boolean copyFiles(String fileContent, String fileName) {
		boolean successful = false;
		try {
/*
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String currentTimestamp = sdf.format(new Date());
			System.out.println("current time" + currentTimestamp);
			fileName = "12345678_" + currentTimestamp;

			listDirectories();
			writeToFile(fileContent, fileName);
			readFromFile(NETWORK_FOLDER + "12345678_" + currentTimestamp + "/" + "12345678_" + currentTimestamp + ".xml");*/
			copy("408913101_DOC_20161018_180533/408913101_DOC_20161018_180535_2.pdf", "U:/sample.pdf");
		/*	delete(NETWORK_FOLDER + fileName+"/");*/

		} catch (Exception e) {
			successful = false;
			e.printStackTrace();
		}
		return successful;
	}

	/**
	 * listDirectories() lists out the sub directories and files
	 */
	public void listDirectories() {
		System.out.println("\n listing directories:");

		SmbFile sFile;
		try {
			sFile = new SmbFile(NETWORK_FOLDER, auth);

			SmbFile[] files;
			try {
				files = sFile.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						System.out.println("Directory");
						System.out.println("File: " + files[i].getName());
						SmbFile[] subdirFiles = files[i].listFiles();
						for (SmbFile smbsubFile : subdirFiles) {
							System.out.println("Sub Directory Listing:" + smbsubFile.getName());
						}
						System.out.println("\n");
					} else {
						System.out.println("File: " + files[i].getName());
					}

				}
			} catch (SmbException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * writeToFile() creates a folder inside NAS drive , creates a file inside
	 * that folder with given file name and writes the file content passed
	 */
	public void writeToFile(String fileContent, String fileName) {
		System.out.println("\n Writing file ......");
		SmbFile sFolder = null;
		String folderPath = NETWORK_FOLDER + fileName + "/";
		String filePath = folderPath + fileName;
		try {

			sFolder = new SmbFile(folderPath, auth);
			if (!sFolder.exists()) {
				sFolder.mkdir();
			}

			SmbFile sFile = new SmbFile(filePath + ".tmp", auth);
			SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
			sfos.write(fileContent.getBytes());
			sfos.close();
			renameTo(sFile, filePath + ".xml");
			System.out.println("file written to " + filePath + ".xml" + "successfuly");
		} catch (SmbException e1) {
			e1.printStackTrace();
			System.out.println("Exception writing file : " + filePath + ".xml");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			System.out.println("Exception writing file : " + filePath + ".xml");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			System.out.println("Exception writing file : " + filePath + ".xml");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception writing file : " + filePath + ".xml");
		}

	}

	/**
	 * readFromFile() prints the file content written in the given file path to
	 * console
	 * 
	 */
	public void readFromFile(String filePath) {
		System.out.println("\n Reading file ......");
		SmbFile f;
		try {
			f = new SmbFile(filePath, auth);
			SmbFileInputStream in = new SmbFileInputStream(f);
			BufferedInputStream bf = new BufferedInputStream(in);
			BufferedReader readr = new BufferedReader(new InputStreamReader(bf));
			System.out.println("file content" + filePath + ":" + readr.readLine());

			readr.close();
			bf.close();
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * copy() copies the file content written in the file path specified in
	 * first parameter to file path specified in second parameter
	 * 
	 */
	public void copy(String sourceFilePath, String dest) throws IOException {
		System.out.println("\n Copying file ......");
		try{
			SmbFile source = new SmbFile(NETWORK_FOLDER + sourceFilePath, auth);
			InputStream is = source.getInputStream();
			OutputStream fileOutputStream = new FileOutputStream(dest);
	
			int bufferSize = 5096;
	
			byte[] b = new byte[bufferSize];
			int noOfBytes = 0;
			while ((noOfBytes = is.read(b)) != -1) {
				fileOutputStream.write(b, 0, noOfBytes);
			}
			is.close();
			fileOutputStream.close();
		}catch(Exception e){
			System.out.println("Error occured while copying file");
			e.printStackTrace();
		}
		System.out.println("copied file successfully to"+dest);
	}

	/**
	 * delete() deletes the folder/file present in given path
	 * 
	 */
	public void delete(String path) {
		System.out.println("\n Deleting file ......");
		SmbFile smbFile;
		try {
			smbFile = new SmbFile(path, auth);
			smbFile.setAttributes(0xFFFF);
			smbFile.delete();
			System.out.println("File with the given path: " + path + " is deleted successfully ");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("Error occured while deleting the file with path : " + path);
		} catch (SmbException e) {
			e.printStackTrace();
			System.out.println("Error occured while deleting the file with path : " + path);
		}

	}

	/**
	 * renameTo() renames a given SMB file with new name passed
	 * 
	 */
	public void renameTo(SmbFile currentFile, String newName) {
		System.out.println("\n Renaming file ......");
		SmbFile f;
		try {
			f = new SmbFile(newName, auth);
			f.exists();
			currentFile.renameTo(f);
		} catch (SmbException e) {
			e.printStackTrace();
			System.out.println("Exception renaming  file to  " + newName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("Exception renaming  file to  " + newName);
		}
	}

}
