package com.example.project_x;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.applandeo.materialcalendarview.CalendarView;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class Calend extends AppCompatActivity implements OnSelectDateListener, OnDayClickListener {
    CalendarView calendarView;
    String name;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    DbEventmain mDBConnector;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calend);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDayClickListener(this);
        Button openManyDaysPickerDialog = (Button) findViewById(R.id.openManyDaysPickerDialogButton);
        mDBConnector = new DbEventmain(this);
        openManyDaysPickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openManyDaysPicker();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button cleardb = findViewById(R.id.clear_db);


        //https://myitschool.ru/book/mod/book/view.php?id=606&chapterid=472
        Calendar calendar = Calendar.getInstance();

        ArrayList<String> olimpiads;
        String[] pranswer;
        String[] secondparth;
        String[] predanswer;
        Integer mesyac;
        HashMap<String, String> datas = new HashMap<>();
        HashMap<String, String> months = new HashMap<>();
        List<EventDay> events = new ArrayList<>();
        String[] shor = {"янв", "фев", "мар", "апр", "мая", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
        String[] lon = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        for (EventDb event : mDBConnector.selectAll()) {
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(sdf.parse(event.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            events.add(new EventDay(cal, R.drawable.citty));
        }
        calendarView.setEvents(events);

        for (int i = 0; i < shor.length; i += 1) {
            //заполнение месяцов под перевод из троки в месяц(мб число у этого месяца)
            months.put(shor[i], lon[i]);
        }
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(openFileInput("olimpiads.txt")));                               //Серёга крут
            String str = "";
            while ((str = read.readLine()) != null) {
                text += str + "\n";
            }
            read.close();
        } catch (FileNotFoundException ex) {
            //Если файла с сохранённым расписанием нет, то записываем в answer пустоту
            text = "";
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String request = "";

        try {
            olimpiads = getIntent().getExtras().getStringArrayList("names");
            request = getIntent().getExtras().getString("request");
            name = getIntent().getExtras().getString("name");
            //доделать запись на олимпиады в календарь
            text = "";
            for (String olimp : olimpiads) {
                text += olimp + "\n";
            }
        } catch (Exception e) {
            // если там нулл, то плохо
        }
        cleardb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBConnector.deleteAll();
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("olimpiads.txt", MODE_PRIVATE)));
                    //добавить вместо кавычек text
                    writer.write("");
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        if (!request.equals("Расписание олимпиады в этом году пока не известно") && !request.equals("")) {
            int end = request.split("\n").length;
            for (int i = 1; i < end; i += 2) {
                String resultat = request.split("\n")[i];
                String answer = "2020-";
                if (resultat.contains("До ")) {
                    pranswer = resultat.replace("До ", "").split(" ");
                    answer += pranswer[0] + "-" + months.get(pranswer[1]) + "!_=_!";
                } else if (resultat.contains("...")) {
                    pranswer = resultat.replace("...", " Q ").split(" Q ");
                    secondparth = pranswer[1].split(" ");
                    mesyac = Integer.valueOf(months.get(secondparth[1]));
                    try {
                        Integer.parseInt(pranswer[0]);
                        answer += pranswer[0] + "-" + months.get(secondparth[1]);
                    } catch (NumberFormatException e) {
                        predanswer = pranswer[0].split(" ");
                        answer += predanswer[0] + "-" + months.get(predanswer[1]);
                    }
                    if (mesyac < 8) {
                        answer += "_#_2021-" + secondparth[0] + "-" + months.get(secondparth[1]);
                    } else {
                        answer += "_#_2020-" + secondparth[0] + "-" + months.get(secondparth[1]);
                    }
                } else {
                    pranswer = resultat.split(" ");
                    for (String element : pranswer) {
                        if (months.containsKey(element)) {
                            answer += months.get(element);
                        } else {
                            answer += element + "-";
                        }
                    }
                }
                datas.put(request.split("\n")[i - 1], answer);
            }
            for (String el : datas.keySet()) {
                Date date = null;
                String interval = datas.get(el);
                assert interval != null;
                List<Date> periods = new ArrayList<>();
                if (interval.contains("!_=_!")) {
                    interval.replace("!_=_!", "");
                    Date currentDate = new Date();
                    String today = sdf.format(currentDate);
                    String lastday = interval;
                    Date start = new Date();
                    Date finish = new Date();
                    try {
                        start = sdf.parse(today);
                        finish = sdf.parse(lastday);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    periods.add(start);
                    periods.add(finish);
                } else if (interval.contains("_#_")) {
                    String[] parasha = interval.split("_#_");
                    String today = parasha[0];
                    String lastday = parasha[1];
                    Date start = new Date();
                    Date finish = new Date();
                    try {
                        start = sdf.parse(today);
                        finish = sdf.parse(lastday);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    periods.add(start);
                    periods.add(finish);
                } else {
                    Date start = new Date();
                    try {
                        start = sdf.parse(interval);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    periods.add(start);
                    periods.add(start);
                }
                for (Date date1 : getInterval(periods.get(0), periods.get(1))) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date1);
                    events.add(new EventDay(cal, R.drawable.citty));

                    mDBConnector.insert(name, sdf.format(date1));
                }

            }
        }
        //все даты в data
        request = "";

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("olimpiads.txt", MODE_PRIVATE)));
            //добавить вместо кавычек text
            if (text == null) {

            } else {
                writer.write(text);
                writer.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView editText = findViewById(R.id.text);
        editText.setText(text);
        //calendar

        String current_data = String.valueOf(calendar.getTime().getDate()) + " : " + String.valueOf(calendar.getTime().getMonth() + 1) + " : " + String.valueOf(calendar.getTime().getYear() + 1900);

    }

    public void openManyDaysPicker() {
        List<Calendar> selectedDays = new ArrayList<>();
        DatePickerBuilder manyDaysBuilder = new DatePickerBuilder(this, this)
                .setPickerType(calendarView.MANY_DAYS_PICKER)
                .setHeaderColor(android.R.color.holo_green_dark)
                .setSelectionColor(android.R.color.holo_green_dark)
                .setTodayLabelColor(android.R.color.holo_green_dark)
                .setDialogButtonsColor(android.R.color.holo_green_dark)
                .setSelectedDays(selectedDays)
                .setNavigationVisibility(View.GONE);

        DatePicker manyDaysPicker = manyDaysBuilder.build();
        manyDaysPicker.show();
    }

    @Override
    public void onSelect(List<Calendar> calendars) {

        Stream.of(calendars).forEach(calendar ->
                mDBConnector.deleteDate(sdf.format(calendar.getTime())));
    }

    public static List<Date> getInterval(
            Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();

        endCalendar.setTime(endDate);
        endCalendar.add(Calendar.DAY_OF_MONTH, 1);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        ArrayList<String> actions = new ArrayList<>();
        Calendar calendar = eventDay.getCalendar();
        String date = sdf.format(calendar.getTime());
        for (EventDb eventDb : mDBConnector.selectAll()) {
            Log.i("11111", eventDb.getDate());
            if (eventDb.getDate().equals(date)) {
                actions.add(eventDb.getEventName());
            }
        }
        Intent intent = new Intent(this, DataInfo.class);
        intent.putExtra("date", date);
        intent.putStringArrayListExtra("actions", actions);
        startActivity(intent);
    }

    //public ContactsContract.Contacts.Data getdatas(ContactsContract.Contacts.Data startDate, ContactsContract.Contacts.Data endDate){
    //var getDates = function(startDate, endDate)
    //
//
    //      var dates = [],
    //    currentDate = startDate,
    //           addDays = function(days) {
    //   var date = new Date(this.valueOf());
    //   date.setDate(date.getDate() + days);
    //   return date;
    //
    //   ;
    //   while (currentDate <= endDate) {
    //       dates.push(currentDate);
    //       currentDate = addDays.call(currentDate, 1);
    //   }
    //   return dates;

}

