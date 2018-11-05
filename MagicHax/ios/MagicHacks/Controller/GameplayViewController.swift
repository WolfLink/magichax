//
//  GameplayViewController.swift
//  MagicHacks
//
//  Created by Marc Davis on 11/3/18.
//  Copyright © 2018 Marc Davis. All rights reserved.
//

import UIKit

class GameplayViewController: ViewController {
    var battle = BattleView(frame: CGRect.zero)
    let player = GamePlayer()
    
    
    @IBOutlet weak var safeView: UIView?

    override func viewWillAppear(_ animated: Bool) {
        battle = BattleView(frame: safeView!.frame)
        safeView?.isHidden = true
        battle.delegate = player
        player.enqueue()
        self.view.addSubview(battle)
        self.view.bringSubviewToFront(battle)
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
