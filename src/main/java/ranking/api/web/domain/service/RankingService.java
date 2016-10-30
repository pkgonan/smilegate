package ranking.api.web.domain.service;

import org.springframework.stereotype.Service;
import ranking.api.web.domain.model.RankingData;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class RankingService {
    final static int PRIORITY_QUEUE_BIG_SIZE = 1000000;
    final static int PRIORITY_QUEUE_SMALL_SIZE = 10;

    public static PriorityBlockingQueue<RankingData> rankingQueue = new PriorityBlockingQueue<RankingData>(PRIORITY_QUEUE_BIG_SIZE, new DataComparator());
    public static Map<String, Integer> allRankingData = new HashMap<String, Integer>();
    public static Map<String, LinkedList<String>> myFriend = new HashMap<String, LinkedList<String>>();

    public void insert(String id, int score) {
        allRankingData.put(id, score);
        if(!myFriend.containsKey(id)) {
            myFriend.put(id, new LinkedList<String>());
            rankingQueue.add(new RankingData(id, score));
        }
        else {
            Iterator<RankingData> iterator = rankingQueue.iterator();
            while(iterator.hasNext()) {
                if(iterator.next().getId().equals(id)) {
                    iterator.remove();
                    rankingQueue.add(new RankingData(id, score));
                    break;
                }
            }
        }
    }

    public void register(String myId, String friendId) {
        final LinkedList<String> friendList = myFriend.get(myId);
        if (!friendList.contains(friendId))
            friendList.add(friendId);
        myFriend.put(myId, friendList);
    }

    public void delete(String myId, String friendId) {
        final LinkedList<String> friendList = myFriend.get(myId);
        friendList.remove(friendId);
    }

    public List<String> getAllRankingList() {
        final List<String> allRankingList = priorityQueueToList(rankingQueue);

        return allRankingList;
    }

    public List<String> getMyRanking(String myId) {
        final PriorityBlockingQueue<RankingData> q = new PriorityBlockingQueue<RankingData>(PRIORITY_QUEUE_SMALL_SIZE, new DataComparator());
        final LinkedList<String> linkedList = new LinkedList<String>();
        int rank = 0;
        q.addAll(rankingQueue);

        while(!q.isEmpty()) {
            final RankingData rankingData = q.poll();
            final String id = rankingData.getId();
            int score = rankingData.getScore();
            ++rank;

            if(id.equals(myId)) {
                final String myRanking = rank + ":" + id + ":" + score;
                linkedList.add(myRanking);
                break;
            }
        }

        return linkedList;
    }

    public List<String> getFriendRankingList(String myId) {
        final LinkedList<String> myFriendList = myFriend.get(myId);
        final PriorityBlockingQueue<RankingData> pq = new PriorityBlockingQueue<RankingData>(PRIORITY_QUEUE_SMALL_SIZE, new DataComparator());
        final Iterator<String> myFriendListIterator = myFriendList.iterator();

        while (myFriendListIterator.hasNext()) {
            final String friendid = myFriendListIterator.next();
            pq.add(new RankingData(friendid, allRankingData.get(friendid)));
        }
        pq.add(new RankingData(myId, allRankingData.get(myId)));

        final List<String> friendRankingList = priorityQueueToList(pq);

        return friendRankingList;
    }

    public List<String> getFriendList(String myId) {
        final LinkedList<String> myFriendList = myFriend.get(myId);

        return myFriendList;
    }

    private  List<String> priorityQueueToList(PriorityBlockingQueue pq) {
        final PriorityBlockingQueue<RankingData> q = new PriorityBlockingQueue<RankingData>(PRIORITY_QUEUE_BIG_SIZE, new DataComparator());
        final List<String> list = new LinkedList<String>();
        int rank = 0;
        q.addAll(pq);

        while(!q.isEmpty()) {
            final RankingData rankData = q.poll();
            final String id = rankData.getId();
            final int score = rankData.getScore();
            final String result = ++rank + ":" + id + ":" + score;
            list.add(result);
        }
        return list;
    }

    private static class DataComparator implements Comparator<RankingData> {
        public int compare(RankingData x, RankingData y) {
            if(x.getScore() > y.getScore())
                return -1;
            else if(x.getScore() < y.getScore())
                return 1;
            else
                return 0;
        }
    }
}