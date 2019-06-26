package lv.edreams.bdc.job

import org.apache.hadoop.hbase.{CellBuilderFactory, CellBuilderType, HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import lv.edreams.bdc.core.dto.Record
import org.apache.hadoop.hbase.util.Bytes

object HBase {
  final val HBASE_HOSTS="hbase"
  final val cfRecords = Bytes.toBytes("records")
  final val valueColumn = Bytes.toBytes("value")

  private val conf = HBaseConfiguration.create()

  conf.set("hbase.zookeeper.quorum", HBASE_HOSTS)

  private val connection = ConnectionFactory.createConnection(conf)
  private val resultsTable = TableName.valueOf(Bytes.toBytes("results"))
  private val table = connection.getTable(resultsTable)

  def write(record: Record): Unit = {
    val rowKey = getRowKey(record)

    val row = new Put(rowKey)

    row.addColumn(cfRecords, valueColumn, Bytes.toBytes(record.toString))

    table.put(row)
  }

  def close(): Unit = {
    table.close()
    connection.close()
  }

  private def getRowKey(record: Record): Array[Byte] = {
    Bytes.toBytes(s"device-${record.deviceId}-${record.time}")
  }
}
