//
//  GamePlayer.swift
//  MagicHacks
//
//  Created by Marc Davis on 11/4/18.
//  Copyright © 2018 Marc Davis. All rights reserved.
//

import Foundation
import Alamofire

class GamePlayer: BattleViewDelegate {
    var lastPressed:Int = 0
    func buttonPressed(_ ID: Int32) {
        lastPressed = Int(ID)
        print("sending a move selection")
        let url = URL(string: "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/626-ios-qovub/service/movemaker/incoming_webhook/move")!
        let id = UIDevice.current.identifierForVendor!.uuidString
        Alamofire.request(url, method: .post, parameters: ["userId":id, "moveSelection":lastPressed], encoding: JSONEncoding.default).responseJSON { (response) in
            print("got a response")
            guard let value = response.result.value as? [String:Any] else {
                print("BAD BAD")
                return
            }
            print(value)
        }
    }
    func sendQuery() {
        
    }
    func enqueue() {
        print("joining the queue")
        let url = URL(string: "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/626-ios-qovub/service/movemaker/incoming_webhook/queue")!
        let id = UIDevice.current.identifierForVendor!.uuidString
        Alamofire.request(url, method: .post, parameters: ["userId":id], encoding: JSONEncoding.default).responseJSON { (response) in
            print("got a response")
            guard let value = response.result.value as? [String:Any] else {
                print("BAD BAD")
                return
            }
            print(value)
        }
    }
}
