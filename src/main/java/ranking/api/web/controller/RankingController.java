package ranking.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;
import ranking.api.web.domain.service.RankingService;
import java.util.List;

@Controller
public class RankingController {
    @Autowired
    private RankingService rankingService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "api/insertRankingData", method = RequestMethod.POST)
    @ResponseBody
    public void insertRankingData(@RequestParam(value = "insertData", required = true) String insertData) {
        final String[] data = insertData.split(":");
        final String id = data[0];
        final int score = Integer.parseInt(data[1]);

        rankingService.insert(id, score);
    }

    @RequestMapping(value = "api/registerFriend", method = RequestMethod.POST)
    @ResponseBody
    public void registerFriend(@RequestParam(value = "registerFriend", required = true) String registerFriend) {
        final String[] data = registerFriend.split(":");
        final String myId = data[0];
        final String friendId = data[1];

        rankingService.register(myId, friendId);
    }

    @RequestMapping(value = "api/deleteFriend", method = RequestMethod.POST)
    @ResponseBody
    public void deleteFriend(@RequestParam(value = "deleteFriend", required = true) String deleteFriend) {
        final String[] data = deleteFriend.split(":");
        final String myId = data[0];
        final String friendId = data[1];

        rankingService.delete(myId, friendId);
    }

    @RequestMapping(value = "api/getAllRanking", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getAllRanking() {
        final List<String> allRankingList =  rankingService.getAllRankingList();

        final ModelAndView mav = new ModelAndView("show");
        mav.addObject("List", allRankingList);

        return mav;
    }

    @RequestMapping(value = "api/getMyRanking", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getMyRanking(@RequestParam(value = "myRankingId", required = true) String myRankingId) {
        final List<String> myRanking = rankingService.getMyRanking(myRankingId);

        final ModelAndView mav = new ModelAndView("show");
        mav.addObject("List", myRanking);

        return mav;
    }

    @RequestMapping(value = "api/getFriendRanking", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getFriendRanking(@RequestParam(value = "friendRankingId", required = true) String friendRankingId) {
        final List<String> friendRanking = rankingService.getFriendRankingList(friendRankingId);

        final ModelAndView mav = new ModelAndView("show");
        mav.addObject("List", friendRanking);

        return mav;
    }

    @RequestMapping(value = "api/getFriendList", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getFriendList(@RequestParam(value = "friendListId", required = true) String friendListId) {
        final List<String> friendList = rankingService.getFriendList(friendListId);

        final ModelAndView mav = new ModelAndView("show");
        mav.addObject("List", friendList);

        return mav;
    }
}