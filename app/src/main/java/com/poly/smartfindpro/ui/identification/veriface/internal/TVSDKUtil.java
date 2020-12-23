package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TVSDKUtil {
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);
    }

    public static Bitmap cropQRImage(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int resizeHeight = 400;
        int resizeWidth = 400;
        if (height < 400) {
            resizeHeight = height;
            resizeWidth = height;
        }
        return Bitmap.createBitmap(bm, (width - resizeWidth) / 2, (height - resizeHeight) / 2, resizeWidth, resizeHeight, null, false);
    }

    public static Bitmap cropQRImage(Bitmap bm, Rect rect) {
        return resize(Bitmap.createBitmap(bm, rect.left, rect.top, rect.width(), rect.height(), null, false), 400, 400);
    }

    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight <= 0 || maxWidth <= 0) {
            return image;
        }
        if (image.getWidth() < maxWidth && image.getHeight() < maxHeight) {
            return image;
        }
        float ratioBitmap = ((float) image.getWidth()) / ((float) image.getHeight());
        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if (((float) maxWidth) / ((float) maxHeight) > ratioBitmap) {
            finalWidth = (int) (((float) maxHeight) * ratioBitmap);
        } else {
            finalHeight = (int) (((float) maxWidth) / ratioBitmap);
        }
        return Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
    }

    public static Bitmap flipFacingBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Matrix matrix = new Matrix();
        if (width < height) {
            height = width;
        }
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    public static Bitmap convertImageToBitmap(Image image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    public static HashMap<String, Object> jsonToMap(JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = (String) keysItr.next();
            Object value = object.get(key);
            if (value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> jsonToList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    private static String getImagePath(Context context, String fileName) {
        return new ContextWrapper(context).getDir("temp", 0).getAbsolutePath() + "/" + fileName;
    }

    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String imageName) {
        if (bitmapImage == null) {
            return null;
        } else {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("temp", 0);
            File mypath = new File(directory, imageName);
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(CompressFormat.PNG, 100, fos);
            } catch (Exception var16) {
                var16.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }

            }

            return directory.getAbsolutePath() + "/" + imageName;
        }
    }

    public static String saveToInternalStorage(Context context, Image image, String imageName) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("temp", 0);
        File mypath = new File(directory, imageName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(mypath);
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            fos.write(bytes);
        } catch (Exception var17) {
            var17.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception var16) {
                var16.printStackTrace();
            }

        }

        return directory.getAbsolutePath() + "/" + imageName;
    }

    public static Bitmap loadImageFromStorage(String path) {
        try {
            File file = new File(path);
            Log.e("aaaa", "File size:" + (file.length() / 1024));
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteImage(Context context, String fileName) {
        try {
            new File(getImagePath(context, fileName)).delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String capitalize(String text) {
        String[] arr = text.trim().split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public static String normalizeFatherName(String text) {
        String[] arr = text.trim().split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].contains("S/O") && !arr[i].contains("W/O") && !arr[i].contains("D/O")) {
                sb.append(arr[i]);
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

   /* public static List<OCRInfo> extractQRTexts(Result result) {
        List<OCRInfo> list = new ArrayList<>();
        if (result != null && !result.getText().isEmpty()) {
            try {
                XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                parser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                parser.setInput(new StringReader(result.getText()));
                for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                    if (eventType == 2) {
                        String name = parser.getName();
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            String attName = parser.getAttributeName(i);
                            String attValue = parser.getAttributeValue(i);
                            if (attName.equals("uid")) {
                                attName = "id";
                            } else if (attName.equals("loc")) {
                                attName = "location";
                            } else if (attName.equals("pc")) {
                                attName = "postal code";
                            } else if (attName.equals("dist")) {
                                attName = "district";
                            } else if (attName.equals("subdist")) {
                                attName = "sub district";
                            } else if (attName.equals("lm")) {
                                attName = "Landmark";
                            } else if (attName.equals("co") || attName.equals("gname") || attName.equals("careOf")) {
                                attName = "guardian's name";
                                attValue = normalizeFatherName(attValue);
                            }
                            list.add(new OCRInfo(capitalize(attName), attValue));
                        }
                    }
                }
            } catch (IOException | XmlPullParserException e) {
            }
        }
        return list;
    }*/

    public static Bitmap base64ToImage(String base64String) {
        byte[] decodedString = Base64.decode(base64String, 0);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static boolean isContainsRect(Rect rect1, Rect rect2) {
        return rect2.left + rect2.width() < rect1.left + rect1.width() && rect2.left > rect1.left && rect2.top > rect1.top && rect2.top + rect2.height() < rect1.top + rect1.height();
    }

    public static byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
