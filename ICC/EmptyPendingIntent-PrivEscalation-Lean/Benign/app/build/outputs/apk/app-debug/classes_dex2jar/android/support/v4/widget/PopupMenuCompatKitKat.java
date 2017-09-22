package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View.OnTouchListener;
import android.widget.PopupMenu;

@TargetApi(19)
@RequiresApi(19)
class PopupMenuCompatKitKat
{
  PopupMenuCompatKitKat() {}
  
  public static View.OnTouchListener getDragToOpenListener(Object paramObject)
  {
    return ((PopupMenu)paramObject).getDragToOpenListener();
  }
}
