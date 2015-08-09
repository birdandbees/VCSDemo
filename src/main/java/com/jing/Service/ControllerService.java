package com.jing.Service;

/**
 * Created by jingjing on 8/2/15.
 */

import com.jing.Model.ChangeSet;
import com.jing.Model.DailySnapshot;
import com.jing.Model.Person;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service
public class ControllerService {
    @Autowired
    private RedisTemplate<String, String> template;

    //private final RedisAtomicLong userIdCounter;

    @Inject
    public ControllerService(StringRedisTemplate template) {
        this.template = template;

    }

    public DailySnapshot getLatest(final String account, final String date)
    {


        List<String> changeHistory = getVersionChangeHistory(account);
        changeHistory

        List<ChangeSet> deltas = getChangeSets(account, changeHistory);

        return reconstruct(deltas, account);

    }

    public ChangeSet addDailySnapshot(final DailySnapshot dailySnapshot) {
        ChangeSet changeSet = new ChangeSet();
        List<String> changeHistory = getVersionChangeHistory(dailySnapshot.getAccount());
        changeSet.setVersion(Parser.getVersionFromDate(dailySnapshot.getDate()));
        if ( changeHistory == null)
        {
            changeSet.setBaseRecord(true);
            changeSet.setDelta(Parser.serializeRecord(dailySnapshot));
            template.opsForValue().set(KeyHelper.getDeltaKey(dailySnapshot.getAccount(),
                    changeSet.getVersion()), changeSet.getDelta());
            template.opsForList().leftPush(KeyHelper.getChangeHistoryKey(dailySnapshot.getAccount()),
                    changeSet.getVersion());
            return changeSet;
        }

        List<ChangeSet> deltas = getChangeSets(dailySnapshot.getAccount(), changeHistory);

        String newDelta = computeDelta( reconstruct(deltas, dailySnapshot.getAccount()),
                dailySnapshot);
        if (newDelta != "")
        {
            changeSet.setDelta(newDelta);
            template.opsForValue().set(KeyHelper.getDeltaKey(dailySnapshot.getAccount(),
                    changeSet.getVersion()), changeSet.getDelta());
            template.opsForList().leftPush(KeyHelper.getChangeHistoryKey(dailySnapshot.getAccount()),
                    changeSet.getVersion());
        }




        return changeSet;
    }

    public DailySnapshot reconstruct(List<ChangeSet> history, String account)
    {
        HashMap<String, String > colChanges = new HashMap<>();
        for (ChangeSet change : history)
        {
            HashMap<String, String> parsed = Parser.parseDelta(change.getDelta());
            for (Map.Entry<String, String>col_name_value :  parsed.entrySet() )
            {
                   colChanges.put(col_name_value.getKey(),col_name_value.getValue());
            }


        }
        DailySnapshot result = new DailySnapshot();
        result.setAccount(account);
        result.setDate(history.get(history.size() - 1).getVersion());
        result.setCol1(colChanges.get("col1"));
        result.setCol1(colChanges.get("col2"));
        result.setCol1(colChanges.get("col3"));
        result.setCol1(colChanges.get("col4"));
        return result;
    }

    public String computeDelta(DailySnapshot old, DailySnapshot now)
    {
        String delta="";
       if ( now.getCol1().compareTo(old.getCol1()) != 0 )
       {
           delta = "col1," + now.getCol1();
       }
        if ( now.getCol1().compareTo(old.getCol1()) != 0 )
        {
            delta = ":col2," + now.getCol2();
        }
        if ( now.getCol1().compareTo(old.getCol1()) != 0 )
        {
            delta = ":col3," + now.getCol3();
        }
        if ( now.getCol1().compareTo(old.getCol1()) != 0 )
        {
            delta = ":col4," + now.getCol4();
        }
        return delta;
    }

    public List<String> getVersionChangeHistory(final String account)
    {
        return  template.boundListOps(KeyHelper.getChangeHistoryKey(account)).range(0,
                template.boundListOps(KeyHelper.getChangeHistoryKey(account)).size());
    }

    public List<ChangeSet> getChangeSets(final String account, List<String> changeHistory)
    {
        List<ChangeSet> result = new ArrayList<ChangeSet>();

        for (String version: changeHistory)
        {
            ChangeSet changeSet = new ChangeSet();
            String changeEntry = (String) template.boundValueOps(KeyHelper.getDeltaKey(account, version)).get();
            changeSet.setVersion(version);
            changeSet.setDelta(changeEntry);
            if (version.compareTo(changeHistory.get(changeHistory.size()-1)) == 0 )
            {
                changeSet.setBaseRecord(true);
            }


        }
        return result;
    }


}
