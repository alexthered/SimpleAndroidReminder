package course.labs.todomanager;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import course.labs.todomanager.ToDoItem.Status;
import course.labs.todomanager.ToDoManagerActivity;
import android.view.Window;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;
	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

    //remove an element at a specific position
    public void remove(int pos){
        Object this_item = getItem(pos);

        mItems.remove(this_item);
        notifyDataSetChanged();
    }

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO - Get the current ToDoItem
		final ToDoItem toDoItem = (ToDoItem) getItem(position);


		// TODO - Inflate the View for this ToDoItem
		// from todo_item.xml
		//RelativeLayout itemLayout = null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout itemLayout = (RelativeLayout) inflater.inflate(R.layout.todo_item, null);


		// Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		// TODO - Display Title in TextView
        final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
        titleView.setText(toDoItem.getTitle());


		// TODO - Set up Status CheckBox
        final CheckBox statusView = (CheckBox) itemLayout.findViewById(R.id.statusCheckBox);
        statusView.setChecked(toDoItem.getStatus()==Status.DONE);


		// TODO - Must also set up an OnCheckedChangeListener,
		// which is called when the user toggles the status checkbox

        statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //log("Entered onCheckedChanged()");

                // Set up and implement an OnCheckedChangeListener, which
                // is called when the user toggles the status checkbox
                if(statusView.isChecked()){
                    toDoItem.setStatus(Status.DONE);
                } else {
                    toDoItem.setStatus(Status.NOTDONE);
                }

                //update the color of the view according to the item's status
                itemLayout.setBackgroundColor(toDoItem.getColor());

            }
        });

		// TODO - Display Priority in a TextView
        final TextView priorityView = (TextView) itemLayout.findViewById(R.id.priorityView);
        priorityView.setText(toDoItem.getPriority().toString());



		// TODO - Display Time and Date.
		// Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and
		// time String
        final TextView dateView = (TextView) itemLayout.findViewById(R.id.dateView);
        dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));

        itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i(TAG, "Enter itemLayout.onLongClick()");

                //show a dialog
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //remove title bar
                dialog.setContentView(R.layout.longclickdialog);
                dialog.setCancelable(true);

                Button deleteButton = (Button) dialog.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "Delete buton is clicked!");

                        //remove item
                        mItems.remove(toDoItem);
                        notifyDataSetChanged();

                        //dismiss the dialog
                        dialog.dismiss();

                    }
                });

                //show the dialog
                dialog.show();

                return true;
            }
        });


        //update the color of the view according to the item's status
        itemLayout.setBackgroundColor(toDoItem.getColor());

		// Return the View you just created
		return itemLayout;

	}
}
