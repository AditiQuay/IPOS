package quay.com.ipos.compliance.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import quay.com.ipos.compliance.data.local.entity.AttachmentEntity;

@Dao
public interface AttachmentDao {

    @Query("SELECT * FROM `attachment`")
    LiveData<List<AttachmentEntity>> loadAttachment();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAttachment(List<AttachmentEntity> attachmentList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAttachment(AttachmentEntity attachmentList);

    @Query("SELECT * FROM 'attachment' WHERE txId=:txId")
    LiveData<List<AttachmentEntity>> getAttachments(long txId);

    @Query("DELETE FROM attachment")
    void delete();
}
