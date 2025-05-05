package vehicool.backend.genericas

import GenericServiceAPI
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.io.Serializable

@Service
abstract class GenericServiceImpl<T : Any, ID : Serializable> : GenericServiceAPI<T, ID> {
    override fun save(entity: T): T {
        return dao.save(entity)
    }

    override fun delete(id: ID) {
        dao.deleteById(id)
    }

    override fun get(id: ID): T? = dao.findById(id).orElse(null)

    override val all: MutableList<T>
        get() = dao.findAll().toMutableList()


    abstract val dao: CrudRepository<T, ID>
}
