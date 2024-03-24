package objstore.fake.transaction

import com.github.yundom.objstore.executor.transaction.TransactionBegin
import com.github.yundom.objstore.executor.transaction.TransactionCommit
import com.github.yundom.objstore.executor.transaction.TransactionRollback
import objstore.fake.FakeCommand

class FakeTransactionBegin: FakeCommand(), TransactionBegin

class FakeTransactionCommit: FakeCommand(), TransactionCommit

class FakeTransactionRollback: FakeCommand(), TransactionRollback