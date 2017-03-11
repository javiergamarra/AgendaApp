package com.nhpatt.agendaapp;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MainActivityTest {

    @Test
    public void shouldRenderARecyclerViewView() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        RecyclerView list = (RecyclerView) activity.findViewById(R.id.list);
        assertNotNull(list);
    }
}