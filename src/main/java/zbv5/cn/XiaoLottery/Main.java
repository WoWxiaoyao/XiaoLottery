package zbv5.cn.XiaoLottery;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import zbv5.cn.XiaoLottery.lang.Lang;
import zbv5.cn.XiaoLottery.util.LotteryUtil;
import zbv5.cn.XiaoLottery.util.PluginUtil;
import zbv5.cn.XiaoLottery.util.PrintUtil;
import zbv5.cn.XiaoLottery.windows.LotteryWindows;

import java.util.regex.Pattern;

public class Main extends PluginBase
{
    private static Main instance;

    public static Main getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        PluginUtil.LoadPlugin();
    }

    @Override
    public void onDisable()
    {
        PluginUtil.DisablePlugin();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getLabel().equalsIgnoreCase("XiaoLottery"))
        {
            if ((args.length == 0) || (args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?")))
            {
                if((sender instanceof Player) && (args.length == 0))
                {
                    if(!sender.hasPermission("Lottery.buy"))
                    {
                        PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
                        return false;
                    }
                    Player p = (Player)sender;
                    p.showFormWindow(LotteryWindows.Windows(p));
                } else {
                    PrintUtil.PrintCommandSender(sender,"&6==== [&bXiaoLottery&6] ====");
                    PrintUtil.PrintCommandSender(sender,"&6/"+label+" &7- &b打开彩票购买页面");
                    PrintUtil.PrintCommandSender(sender,"&6/"+label+"&a buy &e<数量>&7- &b为该玩家手上的物品附魔");
                    PrintUtil.PrintCommandSender(sender,"&6/"+label+"&a draw&7- &b立刻开奖");
                    PrintUtil.PrintCommandSender(sender,"&6/"+label+"&c reload &7- &4重载插件配置");
                }
                return false;
            }
            if(args[0].equalsIgnoreCase("buy"))
            {
                if(!sender.hasPermission("Lottery.buy"))
                {
                    PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
                    return false;
                }
                if(args.length == 2)
                {
                    if(sender instanceof Player)
                    {
                        Pattern pattern = Pattern.compile("[0-9]*");
                        if(!(pattern.matcher(args[1]).matches()))
                        {
                            PrintUtil.PrintCommandSender(sender,Lang.NoInteger);
                            return false;
                        }
                        Player p = (Player)sender;
                        LotteryUtil.BuyTicket(p,Integer.parseInt(args[1]));
                        return true;
                    } else {
                        PrintUtil.PrintCommandSender(sender,Lang.NeedPlayer);
                    }
                }
                PrintUtil.PrintCommandSender(sender,"{prefix}&c正确方式: /"+label+" buy <数量>");
                return false;
            }
            if(!sender.hasPermission("Lottery.admin"))
            {
                PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
                return false;
            }
            if(args[0].equalsIgnoreCase("draw"))
            {
                LotteryUtil.Draw();
                PrintUtil.PrintCommandSender(sender, Lang.Executed);
                return true;
            }
            if(args[0].equalsIgnoreCase("reload"))
            {
                try
                {
                    PluginUtil.reloadLoadPlugin();
                    PrintUtil.PrintCommandSender(sender, Lang.SuccessReload);
                    return true;
                } catch (Exception e)
                {
                    PrintUtil.PrintCommandSender(sender,Lang.FailReload);
                    e.printStackTrace();
                }
                return false;
            }
            PrintUtil.PrintCommandSender(sender, Lang.NullCommand);
            return false;
        }
        return false;
    }
}
