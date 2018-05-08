package quay.com.ipos.productCatalogue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import quay.com.ipos.R;

/**
 * Created by niraj.kumar on 5/8/2018.
 */

public class SegmentedButton extends AppCompatActivity{
    private SegmentedButtonGroup group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy);
        group = (SegmentedButtonGroup) findViewById(R.id.segmentedButtonGroup);
        group.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                Toast.makeText(SegmentedButton.this, "Clicked: " + position, Toast.LENGTH_SHORT).show();

            }
        });

    }
}
