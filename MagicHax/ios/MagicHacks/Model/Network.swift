//
//  Network.swift
//  MagicHacks
//
//  Created by Marc Davis on 11/3/18.
//  Copyright © 2018 Marc Davis. All rights reserved.
//

import Foundation

class GameNetwork {
    var session: URLSession
    
    init() {
        session = URLSession.shared
    }
    
    
    func sendTest() {
        print("trying to do a send")
        let url = URL(string: "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/626-ios-qovub/service/movemaker/incoming_webhook/capthook?nathan=dumb")!
        var request = URLRequest(url: url)
        print("about to set the tig")
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            print("did do a complet")
            if let error = error {
                print(error)
                return
            }
            guard let HTTP = response as? HTTPURLResponse else {
                print("wat")
                return
            }
            print("succcccceess!")
            print(String(data: data!, encoding: .utf8)!)
        }
        print("about to say resume")
        task.resume()
        print("reume")
    }
}
