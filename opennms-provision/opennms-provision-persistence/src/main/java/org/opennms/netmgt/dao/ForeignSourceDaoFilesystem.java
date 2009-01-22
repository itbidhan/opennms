package org.opennms.netmgt.dao;

import java.util.ArrayList;
import java.util.List;

import org.opennms.netmgt.provision.persist.ForeignSourceRepository;
import org.opennms.netmgt.provision.persist.ForeignSourceRepositoryException;
import org.opennms.netmgt.provision.persist.OnmsForeignSource;
import org.opennms.netmgt.provision.persist.RuntimePersistenceException;
import org.springframework.beans.factory.annotation.Autowired;

public class ForeignSourceDaoFilesystem implements ForeignSourceDao {
    @Autowired
    private ForeignSourceRepository m_foreignSourceRepository = null;

    public ForeignSourceDaoFilesystem() {
    }

    public int countAll() {
        return findAll().size();
    }
    
    public List<OnmsForeignSource> findAll() {
        try {
            return new ArrayList<OnmsForeignSource>(m_foreignSourceRepository.getForeignSources());
        } catch (ForeignSourceRepositoryException e) {
            throw new RuntimePersistenceException("unable to get all foreign sources", e);
        }
    }

    public OnmsForeignSource get(String foreignSource) {
        try {
            return m_foreignSourceRepository.getForeignSource(foreignSource);
        } catch (ForeignSourceRepositoryException e) {
            throw new RuntimePersistenceException("unable to get foreign source '" + foreignSource + "'", e);
        }
    }

    public void save(OnmsForeignSource foreignSource) {
        try {
            m_foreignSourceRepository.save(foreignSource);
        } catch (ForeignSourceRepositoryException e) {
            throw new RuntimePersistenceException("unable to save foreign source '" + foreignSource.getName() + "'", e);
        }
    }

    public void delete(OnmsForeignSource foreignSource) {
        try {
            m_foreignSourceRepository.delete(foreignSource);
        } catch (ForeignSourceRepositoryException e) {
            throw new RuntimePersistenceException("unable to delete foreign source '" + foreignSource.getName() + "'", e);
        }
    }
    
    public void setForeignSourceRepository(ForeignSourceRepository fsr) {
        m_foreignSourceRepository = fsr;
    }

}
