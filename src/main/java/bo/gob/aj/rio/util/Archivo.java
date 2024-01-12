package bo.gob.aj.rio.util;

import bo.gob.aj.rio.RioApplication;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Archivo {

    /*public static Integer SubirArchivoServidor(MultipartFile[] pArchivos, String pNombreArchivo) {
        Integer vRespuesta = -1;

        String vNombreArchivoSubido = Arrays.stream(pArchivos)
                                            .map(x -> x.getOriginalFilename())
                                            .filter(y -> !StringUtils.isEmpty(y))
                                            .collect(Collectors.joining(","));

        if (StringUtils.isEmpty(vNombreArchivoSubido)) {
            vRespuesta = HttpStatus.BAD_REQUEST.value();
        }
        else {
            try {
                for (MultipartFile vArchivo: pArchivos) {
                    if (vArchivo.isEmpty()) {
                        continue;
                    }

                    byte[] vBytes = vArchivo.getBytes();
                    File vCarpeta = new File(String.valueOf(RioApplication.CARPETA_ARCHIVOS_RIO));

                    if (!vCarpeta.exists()) {
                        vCarpeta.mkdir();
                    }

                    Path vRuta = Paths.get(RioApplication.CARPETA_ARCHIVOS_RIO + "/" + pNombreArchivo);
                    Files.write(vRuta, vBytes);
                }

                vRespuesta = HttpStatus.OK.value();
            }
            catch(IOException ex) {
                vRespuesta = HttpStatus.UNPROCESSABLE_ENTITY.value();
            }
        }
        return vRespuesta;
    }*/

    public static Integer SubirArchivoServidor(byte[] pArchivo, String pNombreArchivo) {
        Integer vRespuesta = -1;

        if (StringUtils.isEmpty(pNombreArchivo)) {
            vRespuesta = HttpStatus.BAD_REQUEST.value();
        }
        else {
            try {
                File vCarpeta = new File(String.valueOf(RioApplication.CARPETA_ARCHIVOS_RIO));

                if (!vCarpeta.exists()) {
                    vCarpeta.mkdir();
                }

                Path vRuta = Paths.get(RioApplication.CARPETA_ARCHIVOS_RIO + "/" + pNombreArchivo);
                Files.write(vRuta, pArchivo);

                vRespuesta = HttpStatus.OK.value();
            }
            catch(IOException ex) {
                vRespuesta = HttpStatus.UNPROCESSABLE_ENTITY.value();
            }
        }
        return vRespuesta;
    }
}
