package zbv5.cn.XiaoLottery.util;

import zbv5.cn.XiaoLottery.Main;
import zbv5.cn.XiaoLottery.lang.Lang;
import zbv5.cn.XiaoLottery.listener.PlayerListener;

public class PluginUtil
{
    public static void LoadPlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoLottery &e> &d开始加载 &e========");
        if(Main.getInstance().getServer().getPluginManager().getPlugin("EconomyAPI") != null)
        {
            PrintUtil.PrintConsole("&a&l√ &e检测到前置插件 EconomyAPI");
        } else {
            PrintUtil.PrintConsole("&c&l× &e未检测到前置插件 EconomyAPI 插件卸载！");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
        FileUtil.LoadFile();
        Lang.LoadLang();
        Main.getInstance().getServer().getPluginManager().registerEvents(new PlayerListener(), Main.getInstance());
        LotteryUtil.BackUp(true);
        LotteryUtil.run();
        PrintUtil.PrintConsole("&e======== &bXiaoLottery &e> &a加载成功 &e========");
    }

    public static void DisablePlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoLottery &e> &d开始卸载 &e========");
        Main.getInstance().getServer().getScheduler().cancelTask(Main.getInstance());
        LotteryUtil.BackUp(false);
        PrintUtil.PrintConsole("&e> 感谢您的使用,期待下次运行~");
        PrintUtil.PrintConsole("&e======== &bXiaoLottery &e> &c卸载完成 &e========");
    }

    public static void reloadLoadPlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoLottery &e> &d开始重载 &e========");
        LotteryUtil.Draw(null);
        Main.getInstance().getServer().getScheduler().cancelTask(Main.getInstance());
        FileUtil.LoadFile();
        Lang.LoadLang();
        LotteryUtil.run();
        PrintUtil.PrintConsole("&e======== &bXiaoLottery &e> &a重载成功 &e========");
    }
}
