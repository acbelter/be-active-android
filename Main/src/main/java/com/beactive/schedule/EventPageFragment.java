package com.beactive.schedule;

import android.view.View;
import android.widget.TextView;
import com.acbelter.directionalcarousel.page.PageFragment;
import com.acbelter.directionalcarousel.page.PageLayout;
import com.beactive.R;

public class EventPageFragment extends PageFragment<EventItem> {
    @Override
    public View setupPage(PageLayout pageLayout, EventItem pageItem) {
        View pageContent = pageLayout.findViewById(R.id.page_content);

        TextView startTime = (TextView) pageContent.findViewById(R.id.start_time);
        TextView endTime = (TextView) pageContent.findViewById(R.id.end_time);
        TextView title = (TextView) pageContent.findViewById(R.id.title);

        startTime.setText(pageItem.getStartTimeString());
        endTime.setText(pageItem.getEndTimeString());
        title.setText(pageItem.getTitle());
        return pageContent;
    }
}
