package comcesar1287.github.www.collie.view;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import comcesar1287.github.www.collie.CollieDAO;
import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.domain.Atividade;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener, CalendarPickerController{

    private static final String LOG_TAG = ScheduleActivity.class.getSimpleName();
    private List<CalendarEvent> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initToolbar();
        try {
            initComponent();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        eventList = new ArrayList<>();
        try {
            mockList(eventList);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AgendaCalendarView mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.schedule_button_plus:
                startActivity(new Intent(this, AddScheduleActivity.class));
                break;
        }
    }

    private void initToolbar() {
        setTitle("Agenda");
    }

    private void initComponent() throws ParseException {
        ImageView plusScheduler = findViewById(R.id.schedule_button_plus);
        plusScheduler.setOnClickListener(this);

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        eventList = new ArrayList<>();
        mockList(eventList);

        AgendaCalendarView mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
    }

    private void mockList(List<CalendarEvent> eventList) throws ParseException {

        CollieDAO collieDAO = new CollieDAO(this);
        List<Atividade> atividades = collieDAO.getListaAtividades();

        for(Atividade atividade: atividades){
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
            Date data = formato.parse(atividade.getData());

            Log.i("Data", data.toString());
            Log.i("Data da Atividade", atividade.getData());

            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            startTime.setTime(data);
            endTime.setTime(data);

            BaseCalendarEvent event = new BaseCalendarEvent(atividade.getDescricao(), "", atividade.getNome(),
                    ContextCompat.getColor(this, R.color.blue_selected), startTime, endTime, true);
            eventList.add(event);
        }
//        Calendar startTime1 = Calendar.getInstance();
//        Calendar endTime1 = Calendar.getInstance();
//        endTime1.add(Calendar.MONTH, 1);
//        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
//                ContextCompat.getColor(this, R.color.blue_selected), startTime1, endTime1, true);
//        eventList.add(event1);
//
//        Calendar startTime2 = Calendar.getInstance();
//        startTime2.add(Calendar.DAY_OF_YEAR, 1);
//        Calendar endTime2 = Calendar.getInstance();
//        endTime2.add(Calendar.DAY_OF_YEAR, 3);
//        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
//                ContextCompat.getColor(this, R.color.black), startTime2, endTime2, true);
//        eventList.add(event2);

//        // Example on how to provide your own layout
//        Calendar startTime3 = Calendar.getInstance();
//        Calendar endTime3 = Calendar.getInstance();
//        startTime3.set(Calendar.HOUR_OF_DAY, 14);
//        startTime3.set(Calendar.MINUTE, 0);
//        endTime3.set(Calendar.HOUR_OF_DAY, 15);
//        endTime3.set(Calendar.MINUTE, 0);
//        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
//                ContextCompat.getColor(this, R.color.blue_dark), startTime3, endTime3, false, R.drawable.common_ic_googleplayservices);
//        eventList.add(event3);
    }

    @Override
    public void onDaySelected(DayItem dayItem) {
        Log.d(LOG_TAG, String.format("Selected day: %s", dayItem));
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Log.d(LOG_TAG, String.format("Selected event: %s", event));
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        }
    }
}
