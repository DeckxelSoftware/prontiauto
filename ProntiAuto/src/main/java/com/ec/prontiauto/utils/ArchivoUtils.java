package com.ec.prontiauto.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ArchivoUtils {

    public static String archivoToString(String rutaArchivo) {
        /*  70 */ StringBuffer buffer = new StringBuffer();
        try {
            /*  73 */ FileInputStream fis = new FileInputStream(rutaArchivo);
            /*  74 */ InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            /*  75 */ Reader in = new BufferedReader(isr);
            int ch;
            /*  77 */ while ((ch = in.read()) > -1) {
                /*  78 */ buffer.append((char) ch);
            }
            /*  80 */ in.close();
            /*  81 */ return buffer.toString();
        } catch (IOException e) {
            /*  83 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, e);
            /*  84 */        }
        return null;
    }

    public static File stringToArchivo(String rutaArchivo, String contenidoArchivo) {
        /*  97 */ FileOutputStream fos = null;
        /*  98 */ File archivoCreado = null;
        try {
            /* 102 */ fos = new FileOutputStream(rutaArchivo);
            /* 103 */ OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            /* 104 */ for (int i = 0; i < contenidoArchivo.length(); i++) {
                /* 105 */ out.write(contenidoArchivo.charAt(i));
            }
            /* 107 */ out.close();

            /* 109 */ archivoCreado = new File(rutaArchivo);
        } catch (Exception ex) {
            int i;
            /* 112 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            /* 113 */ return null;
        } finally {
            try {
                /* 116 */ if (fos != null) /* 117 */ {
                    fos.close();
                }
            } catch (Exception ex) {
                /* 120 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /* 123 */ return archivoCreado;
    }

    public static byte[] archivoToByte(File file)
            throws IOException {
        /* 136 */ byte[] buffer = new byte[(int) file.length()];
        /* 137 */ InputStream ios = null;
        try {
            /* 139 */ ios = new FileInputStream(file);
            /* 140 */ if (ios.read(buffer) == -1) /* 141 */ {
                throw new IOException("EOF reached while trying to read the whole file");
            }
        } finally {
            try {
                /* 145 */ if (ios != null) /* 146 */ {
                    ios.close();
                }
            } catch (IOException e) {
                /* 149 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        /* 153 */ return buffer;
    }		

    public static boolean byteToFile(byte[] arrayBytes, String rutaArchivo) {
        /* 164 */ boolean respuesta = false;
        try {
            /* 166 */ File file = new File(rutaArchivo);
            /* 167 */ file.createNewFile();
            /* 168 */ FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
            /* 169 */ ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayBytes);
            /* 170 */ OutputStream outputStream = new FileOutputStream(rutaArchivo);
            int data;
            /* 173 */ while ((data = byteArrayInputStream.read()) != -1) {
                /* 174 */ outputStream.write(data);
            }

            /* 177 */ fileInputStream.close();
            /* 178 */ outputStream.close();
            /* 179 */ respuesta = true;
        } catch (IOException ex) {
            /* 181 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* 183 */ return respuesta;
    }

  


    public static boolean copiarArchivo(File archivoOrigen, String pathDestino) {
        /* 574 */ FileReader in = null;
        /* 575 */ boolean resultado = false;
        try {
            /* 578 */ File outputFile = new File(pathDestino);
            /* 579 */ in = new FileReader(archivoOrigen);
            /* 580 */ FileWriter out = new FileWriter(outputFile);
            int c;
            /* 582 */ while ((c = in.read()) != -1) {
                /* 583 */ out.write(c);
            }
            /* 585 */ in.close();
            /* 586 */ out.close();
            /* 587 */ resultado = true;
        } catch (Exception ex) {
            /* 590 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                /* 593 */ in.close();
            } catch (IOException ex) {
                /* 595 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /* 598 */ return resultado;
    }

    public static byte[] ConvertirBytes(String pathArchivo) {
        try {
//            tipoAmbiente();
            FileInputStream in = null;
            //in = new FileInputStream(Utilidades.DirXMLPrincipal + Utilidades.DirFirmados + prefijo + establecimiento + "-" + puntoemision + "-" + secuencial + ".xml");
            in = new FileInputStream(pathArchivo);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           
            DocumentBuilder builder
                    = factory.newDocumentBuilder();
            Document document = builder.parse(in);

            Source source = new DOMSource(document);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Result result = new StreamResult(out);
            TransformerFactory factoryT = TransformerFactory.newInstance();
            Transformer transformer = factoryT.newTransformer();
            transformer.transform(source, result);
            return out.toByteArray();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoUtils.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ParserConfigurationException e) {
            Logger.getLogger(ArchivoUtils.class
                    .getName()).log(Level.SEVERE, null, e);

        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ArchivoUtils.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (TransformerException ex) {
            Logger.getLogger(ArchivoUtils.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static void FileCopy(String sourceFile, String destinationFile) {
        System.out.println("Desde: " + sourceFile);
        System.out.println("Hacia: " + destinationFile);

        try {
            File inFile = new File(sourceFile);
            File outFile = new File(destinationFile);

            FileInputStream in = new FileInputStream(inFile);
            FileOutputStream out = new FileOutputStream(outFile);

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }

            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("Hubo un error de entrada/salida!!!");
        }
    }

  

    /*APROXIMACION DE DECIMALES*/
    public static BigDecimal redondearDecimales(BigDecimal	 valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial.doubleValue();
        parteEntera = Math.floor(resultado);
        Double resutl = BigDecimal.valueOf(resultado).subtract(BigDecimal.valueOf(parteEntera)).doubleValue();
        resultado = resutl * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        BigDecimal resulDes = BigDecimal.valueOf(resultado);
        BigDecimal divide = BigDecimal.valueOf(resulDes.doubleValue()).divide(BigDecimal.valueOf(Math.pow(10, numeroDecimales)));
        resultado = (divide.add(BigDecimal.valueOf(parteEntera))).doubleValue();
        return BigDecimal.valueOf(resultado);
    }
    
    
}
