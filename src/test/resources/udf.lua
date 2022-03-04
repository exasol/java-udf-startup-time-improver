local updatedUdfDef = query([[SELECT TEST.JAVA_UDF_STARTUP_TIME_IMPROVER_INT(
    (SELECT SCRIPT_TEXT FROM SYS.EXA_ALL_SCRIPTS WHERE SCRIPT_NAME=:udfName AND SCRIPT_SCHEMA=:udfSchema),
    :classes,:connection, :bfsPort, :bfsService, :bfsBucket, :pathForDump) AS CMD]],
        { udfName = UDF_NAME, udfSchema = UDF_SCHEMA, classes = CLASSES, connection = CONNECTION_NAME,
          bfsPort = BUCKET_FS_PORT, bfsService = BUCKET_FS_SERVICE,
          bfsBucket = BUCKET_FS_BUCKET, pathForDump = PATH_FOR_DUMP })
query(updatedUdfDef[1].CMD)