require 'pg'

@conn = PGconn.connect('127.0.0.1', 5432, '', '', 'verbum_domini', 'postgres', 'postgres')

def conn
  @conn = PGconn.connect('127.0.0.1', 5432, '', '', 'verbum_domini', 'postgres', 'postgres') if @conn.nil?
  return @conn
end

def next_seq_value(seq)
  _execute("select nextval('#{seq}')").first['nextval']
end

def insert(table, columns)
  sql = 'insert into %s(%s) values(%s)'
  values = columns.values.collect do |v|
    ((v.nil?) ? 'NULL' : "'#{v}'")
  end
  
  sql.sub! '%s', table
  sql.sub! '%s', columns.keys.join(',')
  sql.sub! '%s', values.join(',')

  _execute sql
end

def update(table, columns, conditions)
  sql = 'update %s set %s where %s'
  cols = []
  cond = []
  
  columns.each do |k, v|
    val = "#{k} = "
    val << ((v.nil?) ? 'NULL' : "'#{v}'")
    cols << val
  end
  
  conditions.each do |k, v|
    val = "#{k} = "
    val << ((v.nil?) ? 'NULL' : "'#{v}'")
    cond << val
  end
  
  sql.sub! '%s', table.to_s
  sql.sub! '%s', cols.join(', ')
  sql.sub! '%s', cond.join(' and ')

  _execute sql
end

def query(sql)
  _execute sql
end

def _execute(sql)
  begin
    conn.exec sql
  rescue Exception => ex
    puts "Error ocurred while executing SQL: << #{sql} >>"
    raise ex
  end
end