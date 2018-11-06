package dc.com.dcblueunpair

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import java.util.ArrayList

import dc.com.dcblueunpair.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var datas: ArrayList<BluetoothDevice>? = null
    private var adapter: ListViewAdapter? = null
    private var title:TitleModel?=null
    private var mainBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        datas = ArrayList()
        title = TitleModel("点击我清空蓝牙配对设备")
        mainBinding!!.myTitle = title
        mainBinding!!.hitMe = View.OnClickListener {
            Toast.makeText(this@MainActivity,"击打",Toast.LENGTH_SHORT).show()
            title!!.setTitle("已经清空了所有配对的蓝牙设备")
            removePairDevice()
        }
        adapter = ListViewAdapter(datas!!)
        mainBinding!!.bondList.adapter = adapter
        bondDevice()
    }

    private fun bondDevice() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter.state == BluetoothAdapter.STATE_OFF) {
            title!!.setTitle("等待打开设备蓝牙")
            mainBinding!!.myTitle = title
            mBluetoothAdapter.enable()
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
        val bondedDevices = mBluetoothAdapter.bondedDevices
        if (bondedDevices.isEmpty()) {
            datas!!.clear()
            title!!.setTitle("已经没有配对的蓝牙设备")
            return
        }
        datas!!.addAll(bondedDevices)
        adapter!!.notifyDataSetChanged()
    }

    private fun removePairDevice() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val bondedDevices = mBluetoothAdapter.bondedDevices
        if (bondedDevices.isEmpty()) {
            title!!.setTitle("已经没有配对的蓝牙设备")
            return
        }
        for (device in bondedDevices) {
            unPairDevice(device)
        }
        datas!!.clear()
        adapter!!.notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "DC"
        private fun unPairDevice(device: BluetoothDevice) {
            try {
                val m = device.javaClass
                        .getMethod("removeBond")
                Log.e(TAG, "ok")
                m.invoke(device)
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        }
    }
}
