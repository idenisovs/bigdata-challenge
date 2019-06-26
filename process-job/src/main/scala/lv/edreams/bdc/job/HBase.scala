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

  def write(record: Record) = {
    val rowKey = getRowKey(record)

    val row = new Put(rowKey)

    val cellBuilder = CellBuilderFactory.create(CellBuilderType.DEEP_COPY)

    val cell = cellBuilder.setFamily(cfRecords).setQualifier(valueColumn).setValue(Bytes.toBytes(record.toString)).build()

    row.add(cell)

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
