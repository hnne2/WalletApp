package com.example.wallet.FoundSms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import com.example.wallet.models.Person;

public class FoundSms {
    private static final String ADDRESS_TO_QUERY_Tinkoff = "T-Bank";
    private static final String ADDRESS_TO_QUERY_SberBank = "900";

    public String getSMSMessagesFromAddressTinkoff(ContentResolver contentResolver) {
        String balans="notFound";
        // Получаем URI для всех SMS-сообщений
        Uri uri = Uri.parse("content://sms/");
        // Выполняем запрос к базе данных SMS, фильтруя сообщения по адресу
        Cursor cursor = contentResolver.query(uri, null, Telephony.Sms.ADDRESS + "=?", new String[]{ADDRESS_TO_QUERY_Tinkoff}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
            do {
                // Получаем текст сообщения
                String messageBody = cursor.getString(bodyIndex);
                String[] arrMessageBody= messageBody.split("\\s+");
                for (int i = 0; i < arrMessageBody.length; i++) {
                    if (arrMessageBody[i].equals("Доступно")){
                        balans=arrMessageBody[i+1];
                    }
                }
            } while (cursor.moveToNext()&& balans.equals("notFound"));
            cursor.close();
        }
        return balans;
}

public String getBalansSber(ContentResolver contentResolver){
    String balans="notFound";
    int balansInt = 0;
    Uri uri = Uri.parse("content://sms/");
    Cursor cursor = contentResolver.query(uri, null, Telephony.Sms.ADDRESS + "=?", new String[]{ADDRESS_TO_QUERY_SberBank}, null);
    if (cursor != null && cursor.moveToFirst()) {
        int bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
        do {
            // Получаем текст сообщения
            String messageBody = cursor.getString(bodyIndex);
            String[] arrMessageBody= messageBody.split("\\s+");
            if (arrMessageBody[0].equals("Баланс"))
            {
            for (int i = 4; i < arrMessageBody.length; i=i+2) {
                    System.out.println(arrMessageBody[i]);
                balansInt=balansInt+Integer.parseInt(arrMessageBody[i].split(",")[0].replace("р",""));
            }
                balans = String.valueOf(balansInt);
            }
        } while (cursor.moveToNext()&&balans.equals("notFound"));
        cursor.close();
    }
    return balans;
}
    public Person updateCapital(Person person, ContentResolver contentResolver){

        String tinkoff = getSMSMessagesFromAddressTinkoff(contentResolver);
        String sber = getBalansSber(contentResolver);
        if (!tinkoff.equals("notFound")){
            tinkoff = tinkoff.split(",")[0];
        }
        if (!sber.equals("notFound")){
            sber = sber.split(",")[0];
        }
        if (!sber.equals("notFound") && !tinkoff.equals("notFound")){
            person.setCapital(Integer.parseInt(sber)+Integer.parseInt(tinkoff));
        }
        if (!sber.equals("notFound") && tinkoff.equals("notFound")){
            person.setCapital(Integer.parseInt(sber));
        }
        if (sber.equals("notFound") && !tinkoff.equals("notFound")){
            person.setCapital(Integer.parseInt(tinkoff));
        }
        person.setBalansinsber(sber);
        person.setBalansintinkoff(tinkoff);
        return person;
    }


}
