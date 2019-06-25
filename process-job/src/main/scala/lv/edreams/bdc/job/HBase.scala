package lv.edreams.bdc.job

import org.apache.hadoop.hbase.{CellBuilderFactory, CellBuilderType, HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import lv.edreams.bdc.core.dto.Record
import org.apache.hadoop.hbase.util.Bytes

object HBase {
  final val resultsTable = "results"
  final val cfRecords = Bytes.toBytes("records")
  final val valueColumn = Bytes.toBytes("value")

  private val conf = HBaseConfiguration.create()
  private val connection = ConnectionFactory.createConnection(conf)

  def write(record: Record) = {
    val resultsTable = TableName.valueOf(Bytes.toBytes("results"))

    val table = connection.getTable(resultsTable)

    val rowKey = getRowKey(record)

    val row = new Put(rowKey)

    val cellBuilder = CellBuilderFactory.create(CellBuilderType.DEEP_COPY)

    val cell = cellBuilder.setFamily(cfRecords).setQualifier(valueColumn).setValue(Bytes.toBytes(record.toString)).build()

    row.add(cell)

    table.put(row)

  }

  def getRowKey(record: Record): Array[Byte] = {
    Bytes.toBytes("hello-world")
  }
}
