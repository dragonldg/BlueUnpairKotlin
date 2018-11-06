package dc.com.dcblueunpair

import android.databinding.BaseObservable
import android.databinding.Bindable


class TitleModel internal constructor(private var title: String?) : BaseObservable() {

    val isCanClick: Boolean
        @Bindable
        get() = !title!!.contains("没有配对")

    @Bindable
    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
        notifyPropertyChanged(BR.title)
        notifyPropertyChanged(BR.canClick)
    }
}