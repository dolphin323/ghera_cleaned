package android.support.v7.widget;

class RtlSpacingHelper
{
  public static final int UNDEFINED = Integer.MIN_VALUE;
  private int mEnd = Integer.MIN_VALUE;
  private int mExplicitLeft = 0;
  private int mExplicitRight = 0;
  private boolean mIsRelative = false;
  private boolean mIsRtl = false;
  private int mLeft = 0;
  private int mRight = 0;
  private int mStart = Integer.MIN_VALUE;
  
  RtlSpacingHelper() {}
  
  public int getEnd()
  {
    if (this.mIsRtl) {
      return this.mLeft;
    }
    return this.mRight;
  }
  
  public int getLeft()
  {
    return this.mLeft;
  }
  
  public int getRight()
  {
    return this.mRight;
  }
  
  public int getStart()
  {
    if (this.mIsRtl) {
      return this.mRight;
    }
    return this.mLeft;
  }
  
  public void setAbsolute(int paramInt1, int paramInt2)
  {
    this.mIsRelative = false;
    if (paramInt1 != Integer.MIN_VALUE)
    {
      this.mExplicitLeft = paramInt1;
      this.mLeft = paramInt1;
    }
    if (paramInt2 != Integer.MIN_VALUE)
    {
      this.mExplicitRight = paramInt2;
      this.mRight = paramInt2;
    }
  }
  
  public void setDirection(boolean paramBoolean)
  {
    if (paramBoolean == this.mIsRtl) {
      return;
    }
    this.mIsRtl = paramBoolean;
    if (this.mIsRelative)
    {
      if (paramBoolean)
      {
        int k;
        if (this.mEnd != Integer.MIN_VALUE)
        {
          k = this.mEnd;
          this.mLeft = k;
          if (this.mStart == Integer.MIN_VALUE) {
            break label77;
          }
        }
        label77:
        for (int m = this.mStart;; m = this.mExplicitRight)
        {
          this.mRight = m;
          return;
          k = this.mExplicitLeft;
          break;
        }
      }
      int i;
      if (this.mStart != Integer.MIN_VALUE)
      {
        i = this.mStart;
        this.mLeft = i;
        if (this.mEnd == Integer.MIN_VALUE) {
          break label133;
        }
      }
      label133:
      for (int j = this.mEnd;; j = this.mExplicitRight)
      {
        this.mRight = j;
        return;
        i = this.mExplicitLeft;
        break;
      }
    }
    this.mLeft = this.mExplicitLeft;
    this.mRight = this.mExplicitRight;
  }
  
  public void setRelative(int paramInt1, int paramInt2)
  {
    this.mStart = paramInt1;
    this.mEnd = paramInt2;
    this.mIsRelative = true;
    if (this.mIsRtl)
    {
      if (paramInt2 != Integer.MIN_VALUE) {
        this.mLeft = paramInt2;
      }
      if (paramInt1 != Integer.MIN_VALUE) {
        this.mRight = paramInt1;
      }
    }
    do
    {
      return;
      if (paramInt1 != Integer.MIN_VALUE) {
        this.mLeft = paramInt1;
      }
    } while (paramInt2 == Integer.MIN_VALUE);
    this.mRight = paramInt2;
  }
}
