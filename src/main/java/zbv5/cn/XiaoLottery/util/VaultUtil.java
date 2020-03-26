package zbv5.cn.XiaoLottery.util;

import cn.nukkit.Player;
import me.onebone.economyapi.EconomyAPI;

public class VaultUtil
{
    public static boolean reduceMoney(Player p, double money)
    {
        double hasMoney = getMoney(p);
        if(hasMoney >= money)
        {
            EconomyAPI.getInstance().reduceMoney(p, money);
            return true;
        }
        return false;
    }

    public static void addMoney(Player p, double money)
    {
        EconomyAPI.getInstance().addMoney(p,money);
    }

    public static double getMoney(Player p)
    {
        return EconomyAPI.getInstance().myMoney(p);
    }
}
