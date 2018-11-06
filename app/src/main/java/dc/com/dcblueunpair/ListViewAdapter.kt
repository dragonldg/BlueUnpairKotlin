package dc.com.dcblueunpair

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import dc.com.dcblueunpair.databinding.ItemBinding

import java.util.ArrayList
import android.databinding.DataBindingUtil



class ListViewAdapter(private val datas: ArrayList<BluetoothDevice>) : BaseAdapter() {

    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int): Any {
        return datas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var newConvertView = convertView
        val myHolder: ViewHolder?
        return if (newConvertView == null) {
            newConvertView = LayoutInflater.from(parent.context).inflate(R.layout.layout_listview_item, null)
            myHolder = ViewHolder(newConvertView)
            myHolder.bind(datas[position])
            newConvertView.tag = myHolder
            newConvertView
        } else {
            myHolder = newConvertView.tag as ViewHolder
            myHolder.bind(datas[position])
            newConvertView
        }
    }

    private class ViewHolder(view:View) {
        internal var mBinding: ItemBinding? = null
        init {
            mBinding = DataBindingUtil.bind(view)
        }
        fun bind(name: BluetoothDevice){
            mBinding!!.device = name
        }
    }
}
